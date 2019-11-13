package com.zza.stardust.app.ui.androidhero;

import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.zza.library.base.BasePresenter;
import com.zza.library.utils.LogUtil;
import com.zza.stardust.R;
import com.zza.stardust.base.MActivity;

public class ViewAnimActivity extends MActivity {

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_view_anim;
    }

    public void btnAlpha(View view) {
        AlphaAnimation aa = new AlphaAnimation(0, 1);
        aa.setDuration(1000);
        view.startAnimation(aa);
    }

    public void btnRotate(View view) {
        RotateAnimation ra = new RotateAnimation(0, 360, 100, 100);
        ra.setDuration(1000);
        view.startAnimation(ra);
    }

    public void btnRotateSelf(View view) {
        RotateAnimation ra = new RotateAnimation(0, 360,
                RotateAnimation.RELATIVE_TO_SELF, 0.5F,
                RotateAnimation.RELATIVE_TO_SELF, 0.5F);
        ra.setDuration(1000);
        view.startAnimation(ra);
    }

    public void btnTranslate(View view) {
        TranslateAnimation ta = new TranslateAnimation(0, 200, 0, 300);
        ta.setDuration(1000);
        view.startAnimation(ta);
    }

    public void btnScale(View view) {
        ScaleAnimation sa = new ScaleAnimation(0, 2, 0, 2);
        sa.setDuration(1000);
        view.startAnimation(sa);
    }

    public void btnScaleSelf(View view) {
        ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5F,
                Animation.RELATIVE_TO_SELF, 0.5F);
        sa.setDuration(1000);
        view.startAnimation(sa);
    }

    public void btnSet(View view) {
        AnimationSet as = new AnimationSet(true);
        as.setDuration(1000);

        AlphaAnimation aa = new AlphaAnimation(0, 1);
        aa.setDuration(1000);
        as.addAnimation(aa);

        TranslateAnimation ta = new TranslateAnimation(0, 100, 0, 200);
        ta.setDuration(1000);
        as.addAnimation(ta);

        view.startAnimation(as);
        as.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                LogUtil.i("zza","onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.i("zza","onAnimationEnd");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.i("zza","onAnimationRepeat");
            }
        });
    }

}
