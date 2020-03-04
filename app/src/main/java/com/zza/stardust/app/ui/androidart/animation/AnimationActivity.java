package com.zza.stardust.app.ui.androidart.animation;

import android.content.Intent;
import android.view.View;

import com.zza.library.base.BasePresenter;
import com.zza.stardust.R;
import com.zza.stardust.base.MActivity;

public class AnimationActivity extends MActivity {

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_animation;
    }


    public void onButtonClick(View v) {
        if (v.getId() == R.id.button1) {
            Intent intent = new Intent(this, AnimationTest1Activity.class);
            startActivity(intent);
            overridePendingTransition(R.animator.enter_anim, R.animator.exit_anim);
        } else if (v.getId() == R.id.button2) {
            Intent intent = new Intent(this, AnimationTest1Activity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.button3) {
            Intent intent = new Intent(this, AnimationTest2Activity.class);
            startActivity(intent);
        }
    }

}
