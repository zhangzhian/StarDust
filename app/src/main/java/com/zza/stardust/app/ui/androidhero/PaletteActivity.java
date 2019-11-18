package com.zza.stardust.app.ui.androidhero;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.Window;

import com.zza.library.base.BasePresenter;
import com.zza.stardust.R;
import com.zza.stardust.base.MActivity;

public class PaletteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette);
        onInit(savedInstanceState);

    }

    protected void onInit(Bundle savedInstanceState) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.test_img_m);


        Palette.Builder builder = Palette.from(bitmap);
        builder.generate(new Palette.PaletteAsyncListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onGenerated(Palette palette) {
//        palette.getVibrantSwatch();
//        palette.getDarkMutedSwatch();
//        palette.getLightMutedSwatch();
//        palette.getMutedSwatch();
//        palette.getDarkVibrantSwatch();
//        palette.getLightVibrantSwatch();
//        - Vibrant（充满活力的）
//        - Vibrant dark（充满活力的黑）
//        - Vibrant light（充满活力的白）
//        - Muted(柔和的)
//        - Muted dark(柔和的黑)
//        - Muted light(柔和的白)
                // 创建Palette对象
                // 通过Palette来获取对应的色调
                Palette.Swatch vibrant =
                        palette.getDarkVibrantSwatch();
                // 将颜色设置给相应的组件
                getActionBar().setBackgroundDrawable(
                        new ColorDrawable(vibrant.getRgb()));
                Window window = getWindow();
                window.setStatusBarColor(vibrant.getRgb());
            }
        });
    }
}
