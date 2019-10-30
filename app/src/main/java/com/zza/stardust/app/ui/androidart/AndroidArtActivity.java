package com.zza.stardust.app.ui.androidart;

import android.os.Bundle;

import com.zza.library.base.BasePresenter;
import com.zza.stardust.R;
import com.zza.stardust.base.MActivity;

public class AndroidArtActivity extends MActivity {

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_android_art;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        super.onInit(savedInstanceState);
    }
}
