package com.zza.stardust.app.ui.tboxlog;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zza.library.base.BasePresenter;
import com.zza.library.utils.LogUtil;
import com.zza.library.utils.TimeUtil;
import com.zza.stardust.R;
import com.zza.stardust.app.dialog.CarmakerChooseDialog;
import com.zza.stardust.callback.TransFileCallBack;
import com.zza.stardust.uilts.TFTPClientUtil;
import com.zza.stardust.base.MActivity;
import com.zza.stardust.bean.CarmakerBean;
import com.zza.stardust.common.MAppTypeInfo;
import com.zza.stardust.common.MNetString;

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

    private ArrayList<String> list = new ArrayList<>();
    private ProgressDialog progressDialog = null;
    private StringBuffer bufferShow = new StringBuffer();
    private ArrayList<String> logConfList = new ArrayList<>();
    private CarmakerChooseDialog carmakerChooseDialog = null;

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
        carmakerBean3.setCarmakeName("其他");
        carmakerBean3.setCarmakeIp("192.168.225.1");
        lists.add(carmakerBean3);

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
        tvChooseCarType.setText(bean.getCarmakeName());
    }

    public void updateTextShow(String content) {
        bufferShow.append(content + "\r\n");
        runOnUiThread(() -> {
            tvLogInfo.setText(bufferShow.toString());
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
        });
    }

    private void initLogDataPath() {
        list.clear();
        for (String module : MODULE_NAME) {
            for (int i = 0; i <= 5; i++) {
                if (i == 0) {
                    list.add(TBOX_ARM_LOG + module + ".log");
                } else {
                    list.add(TBOX_ARM_LOG + module + ".log" + "." + (i - 1));
                }
            }
        }
        for (String module : MODULE_NAME_DEBUG) {
            for (int i = 0; i <= 1; i++) {
                if (i == 0) {
                    list.add(TBOX_ARM_LOG + module + ".log");
                } else {
                    list.add(TBOX_ARM_LOG + module + ".log" + "." + (i - 1));
                }
            }
        }
        list.add(TBOX_APPDATA_LOG + "blind_data_file");
        list.add(TBOX_APPDATA_LOG + "qb_blind_data_file");

        for (int i = 0; i < 7; i++) {
            list.add(TBOX_APPDATA_LOG + "weekday_data_" + i);
        }

    }

    private void initLogDataPathByConf() throws Exception {
        list.clear();
        Boolean rules = false;
        for (String conf : logConfList) {
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
                                            list.add(logPath);
                                        } else {
                                            list.add(logPath + "." + (i - 1));
                                        }
                                    }
                                }

                            }
                        }

                    }
                }
            }
        }

        list.add(TBOX_APPDATA_LOG + "blind_data_file");
        list.add(TBOX_APPDATA_LOG + "qb_blind_data_file");

        for (int i = 0; i < 7; i++) {
            list.add(TBOX_APPDATA_LOG + "weekday_data_" + i);
        }

        updateTextShow("size:" + list.size());
    }

    @OnClick({R.id.bt_get_log, R.id.tv_choose_car_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_get_log:
                //pullLog();
                //TODO 权限判断
                pullLogConfFile(LOG_CONF_PATH);
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
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TFTPClientUtil client = new TFTPClientUtil();
                    client.initClient(null, 0);
                    String storageDir = TBOX_FILE_STORAGE_DIR + TimeUtil.stampToDateHMS(System.currentTimeMillis()) + "/";
                    File file = new File(storageDir);
                    if (!file.exists()) file.mkdirs();

                    client.getFile(MNetString.getYD_TFTP_URL(), storageDir, filePath);

                    String[] fileSplit = filePath.split("/");
                    logConfList.clear();
                    logConfList.addAll(getRowStrByFile(storageDir + fileSplit[fileSplit.length - 1]));
                    initLogDataPathByConf();


                } catch (Exception e) {
                    e.printStackTrace();
                    initLogDataPath();
                } finally {
                    pullLog();
                }
            }

        });
        thread.start();
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
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.setMessage("正在获取日志");
                }
            });

            TFTPClientUtil client = new TFTPClientUtil();

            client.initClient(new TransFileCallBack() {
                @Override
                public void onTrans(String direction, String packetData, int progress) {
                    LogUtil.e(direction + " " + packetData + " progress:" + progress);
                }

                @Override
                public void onTransSuccess() {
                    //showlog("onTransSuccess");
                }

                @Override
                public void onTransFail(int errorCode, Exception exception) {
                    LogUtil.e(("onTransFinsh:" + errorCode + " " + exception.getMessage()));
                }
            }, 0);

            String storageDir = TBOX_FILE_STORAGE_DIR + TimeUtil.stampToDateHMS(System.currentTimeMillis()) + "/";
            File file = new File(storageDir);
            if (!file.exists()) file.mkdirs();

            client.getFiles(MNetString.getYD_TFTP_URL(), storageDir, list, null);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (progressDialog != null) {
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
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                }
            });
        }

    }
}
