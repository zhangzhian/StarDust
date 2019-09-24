package com.zza.stardust.app.ui.TFTP;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zza.library.base.BasePresenter;
import com.zza.library.utils.TimeUtil;
import com.zza.stardust.R;
import com.zza.stardust.base.MFragment;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TBoxLogFragment extends MFragment {

    @BindView(R.id.bt_get_log)
    Button btGetLog;
    @BindView(R.id.tv_log_info)
    TextView tvLogInfo;
    private static final String TBOX_FILE_STORAGE_DIR = Environment.getExternalStorageDirectory() + "/TBoxData/";
    private static final String TBOX_ARM_LOG = "data/log/armlog/";
    private static final String TBOX_APPDATA_LOG = "data/data/appdata/";
    private static final String[] moduleName = {"app", "bgd", "crt", "fda", "hum", "ldr", "loc", "mdc", "ntm", "swd"};
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    Unbinder unbinder;
    private ArrayList<String> list = new ArrayList<>();
    private ProgressDialog progressDialog = null;
    private StringBuffer bufferShow = new StringBuffer();

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.fragment_tox_log;
    }

    @Override
    protected void onInit(View rootView, Bundle savedInstanceState) {
        super.onInit(rootView, savedInstanceState);
        File file = new File(TBOX_FILE_STORAGE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        initLogDataPath();
    }


    public void updateTextShow(String content) {
        bufferShow.append(content + "\r\n");
        getActivity().runOnUiThread(() -> {
            tvLogInfo.setText(bufferShow.toString());
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
        });
    }

    private void initLogDataPath() {
        for (String module : moduleName) {
            for (int i = 0; i <= 5; i++) {
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

    @OnClick({R.id.bt_get_log})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_get_log:
                pullLog();
                break;
        }
    }

    private void pullLog() {
        if (progressDialog == null)
            progressDialog = ProgressDialog.show(getActivity(), "提示", "正在传输获取日志", false, false);
        bufferShow.setLength(0);
        tvLogInfo.setText(bufferShow.toString());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TFTPClientUtil client = new TFTPClientUtil();
                    long timeStart = System.currentTimeMillis();
                    client.initClient(getActivity());
//                    client.getFile("192.168.225.1:69",Environment.getExternalStorageDirectory() + "/fda1.log", "data/log/armlog/fda");

                    String storageDir = TBOX_FILE_STORAGE_DIR + TimeUtil.stampToDateHMS(System.currentTimeMillis()) + "/";
                    File file = new File(storageDir);
                    if (!file.exists()) file.mkdirs();

                    client.getFiles("192.168.225.1:69", storageDir, list);

                    long timeEnd = System.currentTimeMillis();
                    getActivity().runOnUiThread(new Runnable() {
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
                    getActivity().runOnUiThread(new Runnable() {
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

}
