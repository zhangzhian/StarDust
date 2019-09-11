package com.zza.stardust.app.ui.TFTP;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

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
                client.initClient();
                client.sendFile("192.168.225.1:69",
                        Environment.getExternalStorageDirectory().getPath() + "/TBOX.bin", "TBOX.bin");
            }
        });
        thread.start();
    }
}
