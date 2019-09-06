package com.zza.stardust.app.ui.ConstraintLayout;

import android.app.Activity;
import android.os.Bundle;

import com.zza.library.base.BasePresenter;
import com.zza.stardust.R;
import com.zza.stardust.base.MActivity;

public class ConstraintLayoutMoreActivity extends MActivity {

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_constraint_layout_more;
    }
}
