package com.zza.stardust.app.ui.TFTP;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.TextView;

import com.zza.library.base.BasePresenter;
import com.zza.library.utils.LogUtil;
import com.zza.stardust.R;
import com.zza.stardust.base.MActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TFTPActivity extends MActivity {


    @BindView(R.id.bt_start)
    Button btStart;
    @BindView(R.id.tv_result)
    TextView tvResult;

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
    }

    @OnClick(R.id.bt_start)
    public void onViewClicked() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                TFTPSendFileClient client = new TFTPSendFileClient();
                long timeStart = System.currentTimeMillis();
                client.initClient();
                client.sendFile("192.168.225.1:69",
                        Environment.getExternalStorageDirectory().getPath() + "/TBOX.bin", "TBOX.bin");
                long timeEnd = System.currentTimeMillis();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvResult.setText("result time: "+(timeEnd - timeStart) / 1000 + "");
                    }
                });
            }

        });
        thread.start();
    }

}
