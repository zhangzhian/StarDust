package com.zza.stardust.app.ui.tboxlog;

import android.text.TextUtils;

import com.zza.library.utils.LogUtil;
import com.zza.library.utils.ValidatorUtil;
import com.zza.stardust.callback.PullFileCallBack;
import com.zza.stardust.callback.TransFileCallBack;
import com.zza.stardust.uilts.TFTPClientUtil;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * @Author: 张志安
 * @Mail: zhangzhian_123@qq.com zhangzhian2016@gmail.com
 * @Description:
 * @CreateDate: 2020/2/4 20:32
 * @UpdateDate: 2020/2/4 20:32
 * @UpdateRemark:
 * @Version: 1.0
 */
public class YodoTBoxGetLogImpl implements YodeTBoxGetLog {

    private volatile static YodoTBoxGetLogImpl uniqueInstance;
    private static boolean isRunning = false;
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

    private YodoTBoxGetLogImpl() {
    }

    public static YodoTBoxGetLogImpl getInstance() {
        if (uniqueInstance == null) {
            synchronized (YodoTBoxGetLogImpl.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new YodoTBoxGetLogImpl();
                }
            }
        }
        return uniqueInstance;
    }

    @Override
    public void TransFiles(final String host, final int post, final String localDir,
                           final ArrayList<String> remoteFilenames,
                           final TransFileCallBack callBack) {

        if (isRunning) {
            callBack.onTransFail(RUNNING_ERROR, new Exception("传输未结束"));
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
        if (TextUtils.isEmpty(localDir)) {
            callBack.onTransFail(FILEPATH_ERROR, new Exception("本地文件路径不能为空"));
            isRunning = false;
            return;
        }
        if (remoteFilenames.size() <= 0) {
            callBack.onTransFail(FILEPATH_ERROR, new Exception("远程文件路径不能为空"));
            isRunning = false;
            return;
        }
        if (callBack == null) {
            callBack.onTransFail(CALLBACK_ERROR, new Exception("CallBack不能为null"));
            isRunning = false;
            return;
        }

        if (!(callBack instanceof PullFileCallBack)) {
            callBack.onTransFail(CALLBACK_ERROR, new Exception("CallBack类型错误"));
            isRunning = false;
            return;
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                TFTPClientUtil client = new TFTPClientUtil();
                long timeStart = System.currentTimeMillis();

                client.initClient(callBack, 0);

                for (int i = 0; i < remoteFilenames.size(); i++) {
                    PullFileCallBack pullFileCallBack = ((PullFileCallBack) callBack);
                    String filePath = remoteFilenames.get(i);
                    String[] fileSplit = filePath.split("/");

                    try {
                        client.getFile(host + ":" + post, localDir, filePath);

                        long timeEnd = System.currentTimeMillis();

                        LogUtil.i("TFTP: time(ms) " + (timeEnd - timeStart));

                        pullFileCallBack.onTransSingleSuccess(filePath, localDir
                                + fileSplit[fileSplit.length - 1] + "");

                        Thread.sleep(1000);

                    } catch (UnknownHostException e) {
                        LogUtil.e("Error: could not resolve hostname.");
                        LogUtil.e(e.getMessage());
                        pullFileCallBack.onTransSingleFail(filePath, localDir
                                + fileSplit[fileSplit.length - 1] + "", HOSTNAME_ERROR, e);
                        isRunning = false;
                    } catch (SocketException e) {
                        LogUtil.e("Error: could not open local UDP socket.");
                        LogUtil.e(e.getMessage());
                        pullFileCallBack.onTransSingleFail(filePath, localDir
                                + fileSplit[fileSplit.length - 1] + "", SOCKET_ERROR, e);
                        isRunning = false;
                    } catch (IOException e) {
                        LogUtil.e("Error: I/O exception occurred while sending file.");
                        LogUtil.e(e.getMessage());
                        pullFileCallBack.onTransSingleFail(filePath, localDir
                                + fileSplit[fileSplit.length - 1] + "", IO_ERROR, e);
                        isRunning = false;
                    } catch (Exception e) {
                        LogUtil.e("Exception");
                        LogUtil.e(e.getMessage());
                        pullFileCallBack.onTransSingleFail(filePath, localDir
                                + fileSplit[fileSplit.length - 1] + "", EXCEPTION, e);
                        isRunning = false;
                    }
                }
                isRunning = false;
                callBack.onTransSuccess();
            }
        });
        thread.start();
    }

    @Override
    public void TransFile(String host, int post, String localDir, String remoteFilename, TransFileCallBack callBack) {

        if (isRunning) {
            callBack.onTransFail(RUNNING_ERROR, new Exception("传输未结束"));
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

        if (TextUtils.isEmpty(localDir)) {
            callBack.onTransFail(FILEPATH_ERROR, new Exception("本地文件路径不能为空"));
            isRunning = false;
            return;
        }

        if (TextUtils.isEmpty(remoteFilename)) {
            callBack.onTransFail(FILEPATH_ERROR, new Exception("远程文件路径不能为空"));
            isRunning = false;
            return;
        }

        if (callBack == null) {
            callBack.onTransFail(CALLBACK_ERROR, new Exception("CallBack不能为null"));
            isRunning = false;
            return;
        }

        if (!(callBack instanceof TransFileCallBack)) {
            callBack.onTransFail(CALLBACK_ERROR, new Exception("CallBack类型错误"));
            isRunning = false;
            return;
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                TFTPClientUtil client = new TFTPClientUtil();

                long timeStart = System.currentTimeMillis();

                String filePath = remoteFilename;
                String[] fileSplit = filePath.split("/");

                client.initClient(callBack, 0);

                try {
                    client.getFile(host + ":" + post, localDir, filePath);

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

    public void ForceSetRunningFalse() {
        isRunning = false;
    }

}
