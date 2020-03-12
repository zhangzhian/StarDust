package com.zza.stardust.app.ui.androidart.crash;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zza.library.base.BasePresenter;
import com.zza.stardust.R;
import com.zza.stardust.base.MActivity;

public class CrashActivity extends MActivity implements View.OnClickListener {
    private Button mButton;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_crash;
    }


    @Override
    protected void onInit(Bundle savedInstanceState) {
        super.onInit(savedInstanceState);
        mButton = (Button) findViewById(R.id.button1);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mButton) {
            // 在这里模拟异常抛出情况，人为抛出一个运行时异常
            throw new RuntimeException("自定义异常：这是自己抛出的异常");
        }
    }
}
