package com.zza.stardust.app.ui.androidhero;

import android.app.Activity;
import android.os.Bundle;

import com.zza.library.base.BasePresenter;
import com.zza.stardust.R;
import com.zza.stardust.app.ui.androidhero.view.LayerView;
import com.zza.stardust.base.MActivity;

public class DrawingSkillsActivity extends MActivity {

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_drawing_skills;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(new LayerView(this));
//    }
}
