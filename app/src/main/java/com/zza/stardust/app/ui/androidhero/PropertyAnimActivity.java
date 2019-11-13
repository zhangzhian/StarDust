package com.zza.stardust.app.ui.androidhero;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zza.library.base.BasePresenter;
import com.zza.stardust.R;
import com.zza.stardust.app.ui.androidhero.anim.CustomAnim;
import com.zza.stardust.app.ui.androidhero.anim.CustomTVAnim;
import com.zza.stardust.base.MActivity;

import java.util.ArrayList;
import java.util.List;

public class PropertyAnimActivity extends MActivity implements View.OnClickListener{

    private int[] mRes = {R.id.imageView_a, R.id.imageView_b, R.id.imageView_c,
            R.id.imageView_d, R.id.imageView_e};
    private List<ImageView> mImageViews = new ArrayList<ImageView>();
    private boolean mFlag = true;


    private LinearLayout mHiddenView;
    private float mDensity;
    private int mHiddenViewMeasuredHeight;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_property_anim;
    }


    @Override
    protected void onInit(Bundle savedInstanceState) {
        super.onInit(savedInstanceState);
        for (int i = 0; i < mRes.length; i++) {
            ImageView imageView = (ImageView) findViewById(mRes[i]);
            imageView.setOnClickListener(this);
            mImageViews.add(imageView);
        }


        mHiddenView = (LinearLayout) findViewById(R.id.hidden_view);
        // 获取像素密度
        mDensity = getResources().getDisplayMetrics().density;
        // 获取布局的高度
        mHiddenViewMeasuredHeight = (int) (mDensity * 40 + 0.5);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_a:
                if (mFlag) {
                    startAnim();
                } else {
                    closeAnim();
                }
                break;
            default:
                Toast.makeText(PropertyAnimActivity.this, "" + v.getId(),
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void closeAnim() {
        ObjectAnimator animator0 = ObjectAnimator.ofFloat(mImageViews.get(0),
                "alpha", 0.5F, 1F);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mImageViews.get(1),
                "translationY", 200F, 0);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mImageViews.get(2),
                "translationX", 200F, 0);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(mImageViews.get(3),
                "translationY", -200F, 0);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(mImageViews.get(4),
                "translationX", -200F, 0);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);
        set.setInterpolator(new BounceInterpolator());
        set.playTogether(animator0, animator1, animator2, animator3, animator4);
        set.start();
        mFlag = true;
    }

    private void startAnim() {
        ObjectAnimator animator0 = ObjectAnimator.ofFloat(
                mImageViews.get(0),
                "alpha",
                1F,
                0.5F);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(
                mImageViews.get(1),
                "translationY",
                200F);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(
                mImageViews.get(2),
                "translationX",
                200F);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(
                mImageViews.get(3),
                "translationY",
                -200F);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(
                mImageViews.get(4),
                "translationX",
                -200F);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);
        set.setInterpolator(new BounceInterpolator());
        set.playTogether(
                animator0,
                animator1,
                animator2,
                animator3,
                animator4);
        set.start();
        mFlag = false;
    }


    public void llClick(View view) {
        if (mHiddenView.getVisibility() == View.GONE) {
            // 打开动画
            animateOpen(mHiddenView);
        } else {
            // 关闭动画
            animateClose(mHiddenView);
        }
    }

    private void animateOpen(final View view) {
        view.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(
                view,
                0,
                mHiddenViewMeasuredHeight);
        animator.start();
    }

    private void animateClose(final View view) {
        int origHeight = view.getHeight();
        ValueAnimator animator = createDropAnimator(view, origHeight, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }
        });
        animator.start();
    }

    private ValueAnimator createDropAnimator(
            final View view, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int value = (Integer) valueAnimator.getAnimatedValue();
                        ViewGroup.LayoutParams layoutParams =
                                view.getLayoutParams();
                        layoutParams.height = value;
                        view.setLayoutParams(layoutParams);
                    }
                });
        return animator;
    }

    public void tvTimer(final View view) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
        valueAnimator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        ((TextView) view).setText("$ " +
                                (Integer) animation.getAnimatedValue());
                    }
                });
        valueAnimator.setDuration(3000);
        valueAnimator.start();
    }

}
