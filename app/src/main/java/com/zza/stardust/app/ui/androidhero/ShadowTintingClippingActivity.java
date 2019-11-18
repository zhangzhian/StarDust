package com.zza.stardust.app.ui.androidhero;

import android.annotation.TargetApi;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.zza.library.base.BasePresenter;
import com.zza.stardust.R;
import com.zza.stardust.base.MActivity;

public class ShadowTintingClippingActivity extends MActivity {

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_shadow_tinting_clipping;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        super.onInit(savedInstanceState);
        setClipping();
    }

    /**
     * Clipping裁剪
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setClipping() {
        final View v1 = findViewById(R.id.tv_rect);
        View v2 = findViewById(R.id.tv_circle);

        //获取Outline
        ViewOutlineProvider vlp1 = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                //修改outline为特定形状
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 30);
            }
        };

        //获取Outline
        ViewOutlineProvider vlp2 = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                //修改outline为特定形状
                outline.setOval(0, 0, view.getWidth(), view.getHeight());
            }
        };
        //重新设置形状
        v1.setOutlineProvider(vlp1);
        v2.setOutlineProvider(vlp2);
    }
}
