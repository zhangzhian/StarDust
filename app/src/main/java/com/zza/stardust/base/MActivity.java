package com.zza.stardust.base;

import android.os.Build;
import android.os.Bundle;

import com.zza.library.base.BaseActivity;
import com.zza.library.utils.ActivityCollector;
import com.zza.library.utils.StatusBarUtil;
import com.zza.stardust.R;


public abstract class MActivity extends BaseActivity {

    @Override
    protected void onInit(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarUtil.transparencyBar(this);
            StatusBarUtil.StatusBarLightMode(this);
        } else {
            StatusBarUtil.setStatusBarColor(this, R.color.black);
        }

        ActivityCollector.addActivity(this);

    }
}
