package com.zza.stardust.app.ui.TFTP;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zza.library.base.BasePresenter;
import com.zza.library.utils.FileChooseUtil;
import com.zza.library.utils.LogUtil;
import com.zza.library.utils.ToastUtil;
import com.zza.stardust.R;
import com.zza.stardust.base.MActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import butterknife.BindView;
import butterknife.OnClick;

public class TFTPActivity extends MActivity {


    private static final int REQUEST_CHOOSEFILE = 1;
    private static final String TBOX_WIFI_ANDROID = "TBOX_WIFI_ANDROID.bin";
    @BindView(R.id.bt_start)
    Button btStart;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.et_filename)
    EditText etFilename;
    @BindView(R.id.tv_filepath)
    TextView tvFilepath;
    @BindView(R.id.bt_download)
    Button btDownload;
    @BindView(R.id.bt_choose_path)
    Button btChoosePath;
    @BindView(R.id.et_sv)
    EditText etSv;
    @BindView(R.id.et_hv)
    EditText etHv;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private ProgressDialog progressDialog = null;
    private static final int CODE_FOR_WRITE_PERMISSION = 1;
    private boolean isPermission = false;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_tftp;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        super.onInit(savedInstanceState);
        LogUtil.i(Environment.getExternalStorageDirectory().getPath() + "/TBOX.bin");
        //使用兼容库就无需判断系统版本
        int hasWriteStoragePermission = ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED) {
            //拥有权限，执行操作
            isPermission = true;
        } else {
            //没有权限，向用户请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_FOR_WRITE_PERMISSION);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //通过requestCode来识别是否同一个请求
        if (requestCode == CODE_FOR_WRITE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //用户同意，执行操作
                isPermission = true;
            } else {
                //用户不同意，向用户展示该权限作用
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    new AlertDialog.Builder(this)
                            .setMessage("需要该权限读取本地升级文件")
                            .setPositiveButton("OK", (dialog1, which) ->
                                    ActivityCompat.requestPermissions(this,
                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                            CODE_FOR_WRITE_PERMISSION))
                            .setNegativeButton("Cancel", null)
                            .create()
                            .show();
                }
            }
        }
    }

    @OnClick({R.id.bt_start, R.id.bt_download, R.id.bt_choose_path})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_start:
                if (TextUtils.isEmpty(tvFilepath.getText().toString())) {
                    ToastUtil.show("请选择路径");
                } else if (TextUtils.isEmpty(etHv.getText().toString())) {
                    ToastUtil.show("输入软件版本号");
                } else if (TextUtils.isEmpty(etSv.getText().toString())) {
                    ToastUtil.show("请选择硬件版本号");
                } else if (etHv.getText().toString().length() >= 31) {
                    ToastUtil.show("请输入正确软件版本号");
                } else if (etSv.getText().toString().length() >= 31) {
                    ToastUtil.show("请输入正确硬件版本号");
                } else if (!isPermission) {
                    ToastUtil.show("无读写权限，请授予权限后重试");
                } else {
                    startUpgrade();
                }
                break;
            case R.id.bt_download:
                break;
            case R.id.bt_choose_path:
                chooseLocalFilePath();
                break;
        }
    }

    private void startUpgrade() {
        if (progressDialog == null)
            progressDialog = ProgressDialog.show(this, "提示", "正在传输升级文件", false, false);
        tvResult.setText("");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TFTPSendFileClient client = new TFTPSendFileClient();
                    long timeStart = System.currentTimeMillis();
                    client.initClient();
                    //client.sendFile("192.168.225.1:69", Environment.getExternalStorageDirectory().getPath() + "/TBOX.bin", "TBOX.bin");
                    client.sendFile("192.168.225.1:69",
                            tvFilepath.getText().toString(), TBOX_WIFI_ANDROID);
                    long timeEnd = System.currentTimeMillis();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.setMessage("正在升级TBox");
                        }
                    });

                    SendTcpData();
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (progressDialog != null) {
                                progressDialog.dismiss();
                                progressDialog = null;
                            }
                        }
                    });
                }
            }

        });
        thread.start();


    }

    private void chooseLocalFilePath() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        //调用系统文件管理器打开指定路径目录
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。

        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CHOOSEFILE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//选择文件返回
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CHOOSEFILE:
                    String path;
                    Uri uri = data.getData();
                    path = FileChooseUtil.getInstance(this).getChooseFileResultPath(uri);
                    tvFilepath.setText(path);
                    LogUtil.i("File path:" + path);
                    break;
            }
        }
    }

    private void SendTcpData() {
        Thread thread = new Thread(() -> {
            try {
                //1.创建客户端Socket，指定服务器地址和端口
                Socket socket = new Socket("192.168.225.1", 60002);
                //2.获取输出流，向服务器端发送信息
                OutputStream os = socket.getOutputStream();//字节输出流
                PrintWriter pw = new PrintWriter(os);
                byte[] data = protocolMake();
                String strData = BytesToHexString(data);

                //3.获取输入流，并读取服务器端的响应信息
                InputStream is = socket.getInputStream();

                //接收服务器的响应
                int line = 0;
                byte[] buf = new byte[1024];
                LogUtil.w(strData);

//                long startTime = System.currentTimeMillis();
                pw.write(strData);
                pw.flush();
                //socket.shutdownOutput();//关闭输出流
                //接收收到的数据
                while ((line = is.read(buf)) != -1) {
//                    long endTime = System.currentTimeMillis();
//                    LogUtil.i("gap:"+(endTime - startTime));
//                    if (endTime - startTime > 10 * 60 * 1000) {
//                        LogUtil.i("刷新超时");
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                tvResult.setText("刷新超时");
//                                if (progressDialog != null) {
//                                    progressDialog.dismiss();
//                                    progressDialog = null;
//                                }
//                            }
//                        });
//                        break;
//                    }

                    //将字节数组转换成十六进制的字符串
                    String strReturn = BytesToHexString(buf);
                    LogUtil.w("---->:" + strReturn);
                    byte[] dataBuf = convertHexToBytes(buf, line);

                    if (dataBuf[20] == 0x2C && dataBuf[21] == 0x01) {
                        LogUtil.i("收到回复：");
                        //收到的wifi升级的回复消息
                        if (dataBuf[23] == 0x02) {
                            // 收到则认为tbox以收到
                            LogUtil.i("开始刷新");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.setMessage("开始刷新TBox");
                                }
                            });
                        } else if (dataBuf[23] == 0x03) {
                            // 1:升级成功2:上次升级没结束3:条件不满足4:用户取消5:用户延迟6:超时
                            // 7:下载失败8:启动信息失败9:校验失败10:pwr为off或acc 11:pwr为on
                            //范围0-19Byte，’\0’结束
                            if (dataBuf[30] == 01) {
                                LogUtil.i("刷新成功");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tvResult.setText("刷新成功");
                                        if (progressDialog != null) {
                                            progressDialog.dismiss();
                                            progressDialog = null;
                                        }
                                    }
                                });
                            } else {
                                LogUtil.i("刷新失败，错误码：" + dataBuf[30]);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tvResult.setText("刷新失败，错误码：" + dataBuf[30]);
                                        if (progressDialog != null) {
                                            progressDialog.dismiss();
                                            progressDialog = null;
                                        }
                                    }
                                });

                            }
                            break;
                        }

                    }
                    Thread.sleep(100);
                }

                //4.关闭资源
                pw.close();
                is.close();
                os.close();

                socket.close();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (progressDialog != null) {
                            tvResult.setText("刷新异常");
                            progressDialog.dismiss();
                            progressDialog = null;
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvResult.setText("刷新异常");
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                            progressDialog = null;
                        }
                    }
                });
            }
        });
        thread.start();
    }

    public byte[] protocolMake() {
        byte[] result = new byte[10 + 20 + 32 * 3 + 1];

        //Header Message Header 报文头，固定4个字节，此处恒为：（0x59,0x44,0x53,0x2E）
        result[0] = 0x59;
        result[1] = 0x44;
        result[2] = 0x53;
        result[3] = 0x2E;
        //Test Flag 测试标志，长度为1个字节。0x00：正常业务；0x01：测试业务。
        result[4] = 0x00;
        //Message Size 编码后的消息长度(Dispatcher Message + Application Data)，长度为2个字节。
        result[5] = 0x00;
        result[6] = 0x74;
        //Dispatcher Message Length 编码后Dispatcher Message 的长度，长度为2个字节。
        result[7] = 0x00;
        result[8] = 0x14;
        // Dispatcher MessageSecurity Context Version Dispatcher Message 密钥版本号，长度为1个字节。为0时为不加密。
        result[9] = 0x00;

        String currentTime = Long.toString(System.currentTimeMillis() / 1000, 16).toUpperCase();
        //Event Creation Time eventCreationTime 4 UINT32 0.. 4294967295 √ √
        //System.arraycopy(currentTime, 0, result, 10, currentTime.length);
        result[10] = (byte) Integer.parseInt((currentTime.substring(0, 2)), 16);
        result[11] = (byte) Integer.parseInt((currentTime.substring(2, 4)), 16);
        result[12] = (byte) Integer.parseInt((currentTime.substring(4, 6)), 16);
        result[13] = (byte) Integer.parseInt((currentTime.substring(6, 8)), 16);
        //Event ID eventId 6 BYTE Size(6)
        result[14] = 0x00;
        result[15] = 0x00;
        result[16] = 0x00;
        result[17] = 0x00;
        result[18] = 0x00;
        result[19] = 0x00;
        //Application ID applicationId 2 UINT16 0.. 65535
        result[20] = 0x2C;
        result[21] = 0x01;
        // Message ID messageId 2 UINT16 0.. 65535
        result[22] = 0x00;
        result[23] = 0x01;
        // Message Counter uplinkCounter 1 UINT8 0..255
        result[24] = 0x03;
        // downlinkCounter 1 UINT8 0..255
        result[25] = 0x00;
        // Application Data Length applicationDataLength 2 UINT16 0.. 65535
        result[26] = 0x00;
        result[27] = 0x60;
        //Result result 2 UINT16 0…65535
        result[28] = 0x00;
        result[29] = 0x00;

        String fileName = TBOX_WIFI_ANDROID;
        System.arraycopy(fileName.getBytes(), 0, result, 30, fileName.length());
        String sv = etSv.getText().toString();
        String hv = etHv.getText().toString();
        System.arraycopy(sv.getBytes(), 0, result, 30 + 32, sv.length());
        System.arraycopy(hv.getBytes(), 0, result, 30 + 32 + 32, hv.length());
        result[126] = getXor(result);
        return result;
    }

    public static byte getXor(byte[] datas) {

        byte temp = datas[0];

        for (int i = 1; i < datas.length - 1; i++) {
            temp ^= datas[i];
        }

        return temp;
    }

    /**
     * 将字节数组转换成十六进制的字符串
     *
     * @return
     */
    public static String BytesToHexString(byte[] bytes) {
        String hexStr = "0123456789ABCDEF";
        String result = "";
        String hex = "";
        for (byte b : bytes) {
            hex = String.valueOf(hexStr.charAt((b & 0xF0) >> 4));
            hex += String.valueOf(hexStr.charAt(b & 0x0F));
            result += hex + "";
        }
        return result;
    }

    public static byte[] convertHexToBytes(byte[] hex, int size) {

        byte[] bytes = new byte[size / 2];

        for (int i = 0; i < size; i += 2) {

            char charTemp1 = (char) hex[i];
            char charTemp2 = (char) hex[i + 1];
            StringBuffer temp = new StringBuffer();
            temp.append(charTemp1);
            temp.append(charTemp2);
            bytes[i / 2] = (byte) Integer.parseInt(temp.toString(), 16);

        }

        return bytes;
    }
}
