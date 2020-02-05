package com.zza.stardust.app.ui.tboxupgrade.upgrade;

import android.text.TextUtils;

import com.zza.library.utils.LogUtil;
import com.zza.library.utils.ValidatorUtil;
import com.zza.stardust.callback.PullFileCallBack;
import com.zza.stardust.uilts.TFTPClientUtil;
import com.zza.stardust.callback.TransFileCallBack;
import com.zza.stardust.callback.UpgradeCallBack;
import com.zza.stardust.uilts.UpgradeUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class YodoTBoxUpgradeImpl implements YodeTBoxUpgrade {

    private volatile static YodoTBoxUpgradeImpl uniqueInstance;
    private static final String TBOX_WIFI_ANDROID = "TBOX_WIFI_ANDROID.bin";
    private static final String TBOX_WIFI_ANDROID_PATH = "cache/upgrade/TBOX_WIFI_ANDROID.bin";
    private static final int NO_ERROR = 0;
    private static final int HOST_ERROR = -1;
    private static final int FILEPATH_ERROR = -2;
    private static final int CALLBACK_ERROR = -3;
    private static final int HOSTNAME_ERROR = -4;
    private static final int IO_ERROR = -5;
    private static final int PORT_ERROR = -6;
    private static final int SOCKET_ERROR = -7;
    private static final int RUNNING_ERROR = -8;
    private static final int EXCEPTION = -99;
    private static boolean isRunning = false;

    private YodoTBoxUpgradeImpl() {
    }

    public static YodoTBoxUpgradeImpl getInstance() {
        if (uniqueInstance == null) {
            synchronized (YodoTBoxUpgradeImpl.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new YodoTBoxUpgradeImpl();
                }
            }
        }
        return uniqueInstance;
    }


    @Override
    public void TransFile(final String host, final int post, final String filePath, final TransFileCallBack callBack) {

        if (isRunning) {
            callBack.onTransFail(RUNNING_ERROR, new Exception("升级未结束"));
            return;
        } else {
            isRunning = true;
        }

        if (TextUtils.isEmpty(host)) {
            callBack.onTransFail(HOST_ERROR, new Exception("IP地址不合法"));
            isRunning = false;
            return;
        }
        if (!ValidatorUtil.isPort(post)) {
            callBack.onTransFail(PORT_ERROR, new Exception("端口号不合法"));
            isRunning = false;
            return;
        }
        if (TextUtils.isEmpty(filePath)) {
            callBack.onTransFail(FILEPATH_ERROR, new Exception("文件路径不能为空"));
            isRunning = false;
            return;
        }
        if (callBack == null) {
            callBack.onTransFail(CALLBACK_ERROR, new Exception("CallBack不能为null"));
            isRunning = false;
            return;
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                TFTPClientUtil client = new TFTPClientUtil();
                long timeStart = System.currentTimeMillis();
                try {

                    File file = new File(filePath);

                    int size = ((int) (file.length() / 2048) + 1) * 2 + 1;

                    client.initClient(callBack, size);

                    client.sendFile(host + ":" + post, filePath, TBOX_WIFI_ANDROID_PATH);
                    long timeEnd = System.currentTimeMillis();
                    LogUtil.i("TFTP: time(ms) " + (timeEnd - timeStart));

                    callBack.onTransSuccess();
                    isRunning = false;
                } catch (UnknownHostException e) {
                    LogUtil.e("Error: could not resolve hostname.");
                    LogUtil.e(e.getMessage());
                    callBack.onTransFail(HOSTNAME_ERROR, e);
                    isRunning = false;
                } catch (SocketException e) {
                    LogUtil.e("Error: could not open local UDP socket.");
                    LogUtil.e(e.getMessage());
                    callBack.onTransFail(SOCKET_ERROR, e);
                    isRunning = false;
                } catch (IOException e) {
                    LogUtil.e("Error: I/O exception occurred while sending file.");
                    LogUtil.e(e.getMessage());
                    callBack.onTransFail(IO_ERROR, e);
                    isRunning = false;
                } catch (Exception e) {
                    LogUtil.e("Exception");
                    LogUtil.e(e.getMessage());
                    callBack.onTransFail(EXCEPTION, e);
                    isRunning = false;
                }


            }
        });
        thread.start();
    }

    @Override
    public void UpgradeTBox(final String host, final int post, final String softVersion,
                            final String hardVersion, final UpgradeCallBack callBack) {
        final Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Socket socket = null;
                OutputStream os = null;//字节输出流
                PrintWriter pw = null;
                InputStream is = null;

                try {
                    //1.创建客户端Socket，指定服务器地址和端口
                    socket = new Socket(host, post);
                    //2.获取输出流，向服务器端发送信息
                    os = socket.getOutputStream();//字节输出流
                    pw = new PrintWriter(os);
                    byte[] data = UpgradeUtils.protocolMake(softVersion, hardVersion, TBOX_WIFI_ANDROID);
                    String strData = UpgradeUtils.bytesToHexString(data);

                    //3.获取输入流，并读取服务器端的响应信息
                    is = socket.getInputStream();

                    //接收服务器的响应
                    int line = 0;
                    byte[] buf = new byte[1024];
                    LogUtil.w(strData);

                    pw.write(strData);
                    pw.flush();
                    //socket.shutdownOutput();//关闭输出流
                    //接收收到的数据
                    while ((line = is.read(buf)) != -1) {

                        //将字节数组转换成十六进制的字符串
                        String strReturn = UpgradeUtils.bytesToHexString(buf);
                        LogUtil.w("---->:" + strReturn);
                        byte[] dataBuf = UpgradeUtils.convertHexToBytes(buf, line);

                        if (dataBuf[20] == 0x2C && dataBuf[21] == 0x01) {
                            LogUtil.i("收到回复：");
                            //收到的wifi升级的回复消息
                            if (dataBuf[23] == 0x02) {
                                // 收到则认为tbox以收到
                                LogUtil.i("开始刷新");
                                callBack.onUpgradeStart();
                            } else if (dataBuf[23] == 0x03) {
                                // 1:升级成功2:上次升级没结束3:条件不满足4:用户取消5:用户延迟6:超时
                                // 7:下载失败8:启动信息失败9:校验失败10:pwr为off或acc 11:pwr为on
                                //范围0-19Byte，’\0’结束
                                if (dataBuf[30] == 01) {
                                    LogUtil.i("刷新成功");

                                    callBack.onUpgradeSuccess();

                                } else {
                                    LogUtil.i("刷新失败，错误码：" + dataBuf[30]);
                                    callBack.onUpgradeFail(dataBuf[30],
                                            new Exception(UpgradeUtils.errorCode2Str(dataBuf[30])));
                                }
                                break;
                            }

                        }
                        Thread.sleep(100);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    callBack.onUpgradeFail((byte) EXCEPTION, e);
                } finally {
                    //4.关闭资源
                    try {
                        if (is != null)
                            is.close();
                        if (os != null)
                            os.close();
                        if (socket != null)
                            socket.close();
                        if (pw != null)
                            pw.close();
                        isRunning = false;
                    } catch (IOException e) {
                        e.printStackTrace();
                        callBack.onUpgradeFail((byte) EXCEPTION, e);
                        isRunning = false;
                    }

                }
            }
        });
        thread.start();
    }

    public void ForceSetRunningFalse(){
        isRunning = false;
    }

}
