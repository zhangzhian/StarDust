package com.zza.stardust.app.ui.androidhero;

import android.os.Bundle;
import android.widget.TextView;

import com.zza.library.base.BasePresenter;
import com.zza.stardust.R;
import com.zza.stardust.base.MActivity;

public class SystemInfoActivity extends MActivity {

    private TextView tvSystemInfo;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_system_info;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        super.onInit(savedInstanceState);
        tvSystemInfo = findViewById(R.id.tv_system_info);
        tvSystemInfo.setText(SystemInfoTools.getBuildInfo() + "\r\n" + SystemInfoTools.getSystemPropertyInfo());
    }
}
