package com.zza.stardust.app.ui.tboxlog;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zza.library.base.BasePresenter;
import com.zza.library.utils.LogUtil;
import com.zza.library.utils.TimeUtil;
import com.zza.library.utils.ToastUtil;
import com.zza.stardust.R;
import com.zza.stardust.app.dialog.CarmakerChooseDialog;
import com.zza.stardust.callback.PullFileCallBack;
import com.zza.stardust.callback.TransFileCallBack;
import com.zza.stardust.base.MActivity;
import com.zza.stardust.bean.CarmakerBean;
import com.zza.stardust.common.MAppTypeInfo;
import com.zza.stardust.common.MNetInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GetLogActivity extends MActivity {

    @BindView(R.id.bt_get_log)
    Button btGetLog;
    @BindView(R.id.tv_log_info)
    TextView tvLogInfo;
    private static final int CODE_FOR_WRITE_PERMISSION = 1;
    private static final String TBOX_FILE_STORAGE_DIR = Environment.getExternalStorageDirectory() + "/TBoxData/";
    private static final String TBOX_ARM_LOG = "data/log/armlog/";
    private static final String TBOX_APPDATA_LOG = "data/data/appdata/";
    private static final String[] MODULE_NAME = {"app", "bgd", "crt", "fda", "hum", "ldr", "loc", "mdc", "ntm", "swd", "rdt", "mda"};
    private static final String[] MODULE_NAME_DEBUG = {"appd", "bgdd", "crtd", "fdad", "humd", "ldrd", "locd", "mdcd", "ntmd", "swdd", "rdtd", "mdad"};
    private static final String LOG_CONF_PATH = "data/yds/yds_log.conf";

    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.tv_choose_car_type)
    TextView tvChooseCarType;
    @BindView(R.id.textView5)
    TextView textView5;

    private ArrayList<String> filePathList = new ArrayList<>();
    private ProgressDialog progressDialog = null;
    private StringBuffer bufferShow = new StringBuffer();
    private ArrayList<String> logConfDataList = new ArrayList<>();
    private CarmakerChooseDialog carmakerChooseDialog = null;
    private CarmakerBean carmakerBeanChoose = null;
    private boolean isPermission = false;


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_get_log;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        super.onInit(savedInstanceState);

        //使用兼容库就无需判断系统版本
        int hasWriteStoragePermission = ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED) {
            //拥有权限，执行操作
            isPermission = true;
        } else {
            //没有权限，向用户请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_FOR_WRITE_PERMISSION);
        }

        File file = new File(TBOX_FILE_STORAGE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }

        adaptationVersion();
    }

    private void adaptationVersion() {
        if (MAppTypeInfo.APP_TYPE == MAppTypeInfo.APP_YD_ZT) {
            textView5.setVisibility(View.GONE);
            tvChooseCarType.setVisibility(View.GONE);
            carmakerBeanChoose = new CarmakerBean();
            carmakerBeanChoose.setCarmakeId("1");
            carmakerBeanChoose.setCarmakeName("众泰");
            carmakerBeanChoose.setCarmakeIp("192.168.100.1");
        } else {
            initCarmakerDialog();
        }
    }


    private void initCarmakerDialog() {
        List<CarmakerBean> lists = new ArrayList<>();
        CarmakerBean carmakerBean1 = new CarmakerBean();
        carmakerBean1.setCarmakeId("1");
        carmakerBean1.setCarmakeName("众泰");
        carmakerBean1.setCarmakeIp("192.168.100.1");
        lists.add(carmakerBean1);
        CarmakerBean carmakerBean2 = new CarmakerBean();
        carmakerBean2.setCarmakeId("2");
        carmakerBean2.setCarmakeName("云度");
        carmakerBean2.setCarmakeIp("192.168.225.1");
        lists.add(carmakerBean2);
        CarmakerBean carmakerBean3 = new CarmakerBean();
        carmakerBean3.setCarmakeId("3");
        carmakerBean3.setCarmakeName("通用");
        carmakerBean3.setCarmakeIp("192.168.225.1");
        lists.add(carmakerBean3);
        carmakerBeanChoose = carmakerBean3;
        carmakerChooseDialog = new CarmakerChooseDialog(this, lists) {
            @Override
            public void onChooseReviewer(View view, int position) {
                //MClientString.TFTP_IP = lists.get(position).getCarmakeIp();
                chooseCarmakerData(lists.get(position));
                dismiss();
            }
        };
    }

    public void chooseCarmakerData(CarmakerBean bean) {
        //LogUtil.i(bean.toString());
        carmakerBeanChoose = bean;
        tvChooseCarType.setText(carmakerBeanChoose.getCarmakeName());
    }


    private void initLogDataPath() {
        filePathList.clear();
        for (String module : MODULE_NAME) {
            for (int i = 0; i <= 5; i++) {
                if (i == 0) {
                    filePathList.add(TBOX_ARM_LOG + module + ".log");
                } else {
                    filePathList.add(TBOX_ARM_LOG + module + ".log" + "." + (i - 1));
                }
            }
        }
        for (String module : MODULE_NAME_DEBUG) {
            for (int i = 0; i <= 1; i++) {
                if (i == 0) {
                    filePathList.add(TBOX_ARM_LOG + module + ".log");
                } else {
                    filePathList.add(TBOX_ARM_LOG + module + ".log" + "." + (i - 1));
                }
            }
        }
        filePathList.add(TBOX_APPDATA_LOG + "blind_data_file");
        filePathList.add(TBOX_APPDATA_LOG + "qb_blind_data_file");

        for (int i = 0; i < 7; i++) {
            filePathList.add(TBOX_APPDATA_LOG + "weekday_data_" + i);
        }

    }

    private void initLogDataPathByConf() throws Exception {
        filePathList.clear();
        Boolean rules = false;
        for (String conf : logConfDataList) {
            if ("[rules]".equals(conf)) {
                rules = true;
                continue;
            }
            if (rules) {
                if (!conf.startsWith("#") && !TextUtils.isEmpty(conf)) {
                    String[] splitePath = conf.split("\"");
                    if (splitePath != null && splitePath.length == 3) {
                        String logPath = splitePath[1];
                        if (splitePath[2].startsWith(",")) {
                            splitePath[2].replace(",", "");
                            String[] spliteBuffSize = splitePath[2].split(";");
                            if (spliteBuffSize != null && spliteBuffSize.length == 2) {
                                String[] buffSize = spliteBuffSize[0].split("\\*");
                                if (buffSize != null && buffSize.length == 2) {
                                    for (int i = 0; i <= Integer.parseInt(buffSize[1]); i++) {
                                        if (i == 0) {
                                            filePathList.add(logPath);
                                        } else {
                                            filePathList.add(logPath + "." + (i - 1));
                                        }
                                    }
                                }

                            }
                        }

                    }
                }
            }
        }

        filePathList.add(TBOX_APPDATA_LOG + "blind_data_file");
        filePathList.add(TBOX_APPDATA_LOG + "qb_blind_data_file");

        for (int i = 0; i < 7; i++) {
            filePathList.add(TBOX_APPDATA_LOG + "weekday_data_" + i);
        }

        showlog("size:" + filePathList.size());
    }

    @OnClick({R.id.bt_get_log, R.id.tv_choose_car_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_get_log:
                if (!isPermission) {
                    ToastUtil.show("无读写权限，请授予权限后重试");
                } else {
                    pullLogConfFile(LOG_CONF_PATH);
                }
                break;
            case R.id.tv_choose_car_type:
                chooseCarmaker(view);
                break;
        }
    }

    private void pullLogConfFile(String filePath) {
        if (progressDialog == null)
            progressDialog = ProgressDialog.show(this, "提示", "正在传输获取日志配置文件", false, false);
        bufferShow.setLength(0);
        tvLogInfo.setText(bufferShow.toString());
        String storageDir = TBOX_FILE_STORAGE_DIR + TimeUtil.stampToDateHMS(System.currentTimeMillis()) + "/";
        File file = new File(storageDir);
        if (!file.exists()) file.mkdirs();

        YodoTBoxGetLogImpl.getInstance().TransFile(carmakerBeanChoose.getCarmakeIp(),
                MNetInfo.YD_TFTP_PORT, storageDir, filePath, new TransFileCallBack() {

                    @Override
                    public void onTrans(String direction, String packetData, int progress) {
                        LogUtil.i(direction + " " + packetData + " progress:" + progress);
                    }

                    @Override
                    public void onTransSuccess() {
                        String[] fileSplit = filePath.split("/");
                        logConfDataList.clear();
                        logConfDataList.addAll(getRowStrByFile(storageDir + fileSplit[fileSplit.length - 1]));
                        try {
                            initLogDataPathByConf();
                            pullLog();
                        } catch (Exception e) {
                            e.printStackTrace();
                            initLogDataPath();
                            pullLog();
                        }
                    }

                    @Override
                    public void onTransFail(int errorCode, Exception exception) {
                        initLogDataPath();
                        pullLog();
                    }
                });
    }

    public static List<String> getRowStrByFile(String name) {
        // 使用ArrayList来存储每行读取到的字符串
        List<String> arrayList = new ArrayList<>();
        try {
            FileReader fr = new FileReader(name);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
                LogUtil.i(str + "");
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    private void chooseCarmaker(View view) {
        carmakerChooseDialog.showDialog();
    }

    private void pullLog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.setMessage("正在获取日志");
            }
        });

        String storageDir = TBOX_FILE_STORAGE_DIR + TimeUtil.stampToDateHMS(System.currentTimeMillis()) + "/";
        File file = new File(storageDir);
        if (!file.exists()) file.mkdirs();

        YodoTBoxGetLogImpl.getInstance().TransFiles(carmakerBeanChoose.getCarmakeIp(),
                MNetInfo.YD_TFTP_PORT, storageDir, filePathList, new PullFileCallBack() {
                    @Override
                    public void onTransSingleSuccess(String remoteFileName, String localFilePath) {
                        String[] fileSplit = remoteFileName.split("/");
                        showlog("[" + fileSplit[fileSplit.length - 1] + "]Success: " + localFilePath + "");
                    }

                    @Override
                    public void onTransSingleFail(String remoteFileName, String localFilePath, int errorCode, Exception exception) {
                        String[] fileSplit = remoteFileName.split("/");
                        showlog("[" + fileSplit[fileSplit.length - 1] + "]Fail: " + errorCode + " " + exception.getMessage());
                    }

                    @Override
                    public void onTrans(String direction, String packetData, int progress) {
                        LogUtil.i(direction + " " + packetData + " progress:" + progress);
                    }

                    @Override
                    public void onTransSuccess() {
                        runOnUiThread(() -> {
                            if (progressDialog != null) {
                                progressDialog.dismiss();
                                progressDialog = null;
                            }
                        });
                    }

                    @Override
                    public void onTransFail(int errorCode, Exception exception) {
                        showlog("Fail: " + errorCode + " " + exception.getMessage());

                        runOnUiThread(() -> {
                            if (progressDialog != null) {
                                progressDialog.dismiss();
                                progressDialog = null;
                            }
                        });
                    }
                });
    }

    private void showlog(String log) {
        bufferShow.append(log + "\r\n");
        runOnUiThread(() -> {
            tvLogInfo.setText(bufferShow.toString());
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
        });

    }

    private void clearlog() {
        bufferShow.setLength(0);
        runOnUiThread(() -> {
            tvLogInfo.setText(bufferShow.toString());
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
        });
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
                            .setMessage("需要该权限读写本地文件")
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
}
