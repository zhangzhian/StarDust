package com.zza.stardust.app.ui.androidhero;

import android.view.View;

import com.zza.library.base.BasePresenter;
import com.zza.stardust.R;
import com.zza.stardust.app.ui.androidhero.anim.CustomAnim;
import com.zza.stardust.app.ui.androidhero.anim.CustomTVAnim;
import com.zza.stardust.base.MActivity;

public class CustomAnimActivity extends MActivity {

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_custom_anim;
    }


    public void btnAnim(View view) {
        CustomAnim customAnim = new CustomAnim();
        customAnim.setRotateY(30);
        view.startAnimation(customAnim);
    }

    public void imgClose(View view) {
        CustomTVAnim customTV = new CustomTVAnim();
        view.startAnimation(customTV);
    }
}
