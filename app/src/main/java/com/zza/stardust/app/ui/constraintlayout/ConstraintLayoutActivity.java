package com.zza.stardust.app.ui.constraintlayout;

import android.content.Intent;
import android.view.View;

import com.zza.library.base.BasePresenter;
import com.zza.stardust.R;
import com.zza.stardust.base.MActivity;

public class ConstraintLayoutActivity extends MActivity {

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_constraint_layout;
    }


    public void clickMore(View view) {
        startActivity(new Intent(this, ConstraintLayoutMoreActivity.class));
    }
}
