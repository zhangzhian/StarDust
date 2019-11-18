package com.zza.stardust.app.ui.androidhero;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import com.zza.library.base.BasePresenter;
import com.zza.stardust.R;
import com.zza.stardust.base.MActivity;

public class OverAnimationActivity extends MActivity {

    private Intent intent;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_over_animation;
    }

    // 设置不同动画效果
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void explode(View view) {
        intent = new Intent(this, OverAnimationTargetActivity.class);
        intent.putExtra("flag", 0);
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this)
                        .toBundle());
    }
    // 设置不同动画效果
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void slide(View view) {
        intent = new Intent(this, OverAnimationTargetActivity.class);
        intent.putExtra("flag", 1);
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this)
                        .toBundle());
    }
    // 设置不同动画效果
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void fade(View view) {
        intent = new Intent(this, OverAnimationTargetActivity.class);
        intent.putExtra("flag", 2);
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this)
                        .toBundle());
    }
    // 设置不同动画效果
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void share(View view) {
        View fab = findViewById(R.id.fab_button);
        intent = new Intent(this, OverAnimationTargetActivity.class);
        intent.putExtra("flag", 3);
        // 创建单个共享元素
//        startActivity(intent,
//                ActivityOptions.makeSceneTransitionAnimation(
//                        this, view, "share").toBundle());
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(
                        this,
                        // 创建多个共享元素
                        Pair.create(view, "share"),
                        Pair.create(fab, "fab")).toBundle());
    }

}
