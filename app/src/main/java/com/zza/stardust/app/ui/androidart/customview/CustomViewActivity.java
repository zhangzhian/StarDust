package com.zza.stardust.app.ui.androidart.customview;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import com.zza.library.base.BasePresenter;
import com.zza.library.utils.LogUtil;
import com.zza.stardust.R;
import com.zza.stardust.base.MActivity;

public class CustomViewActivity extends MActivity  implements View.OnClickListener {

    private Button view;
    private View mButton2;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_custom_view2;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        super.onInit(savedInstanceState);
        initView();
        measureView();
    }

    private void initView() {
        view = (Button) findViewById(R.id.button1);
        view.setOnClickListener(this);
        mButton2 = (TextView) findViewById(R.id.button2);
    }

    private void measureView() {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec((1 << 30) - 1, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(100, View.MeasureSpec.EXACTLY);
        view.measure(widthMeasureSpec, heightMeasureSpec);
        LogUtil.d( "measureView, width= " + view.getMeasuredWidth() + " height= " + view.getMeasuredHeight());
    }

    @Override
    protected void onStart() {
        super.onStart();
        view.post(new Runnable() {

            @Override
            public void run() {
                int width = view.getMeasuredWidth();
                int height = view.getMeasuredHeight();
                LogUtil.d( "view.post, width= " + width + " height= " + height);
            }
        });

        ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int width = view.getMeasuredWidth();
                int height = view.getMeasuredHeight();
                LogUtil.d( "addOnGlobalLayoutListener, width= " + width + " height= " + height);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            int width = view.getMeasuredWidth();
            int height = view.getMeasuredHeight();
            LogUtil.d("onWindowFocusChanged, width= " + width + " height= " + height);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == view) {
            LogUtil.d("measure width= " + mButton2.getMeasuredWidth() + " height= " + mButton2.getMeasuredHeight());
            LogUtil.d("layout width= " + mButton2.getWidth() + " height= " + mButton2.getHeight());
        }
    }
}
