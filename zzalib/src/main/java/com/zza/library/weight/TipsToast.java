package com.zza.library.weight;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zza.library.R;


/**
 * @Author: 张志安
 * @Mail: zhangzhian2016@gmail.com
 *
 * 自定义的Toast，用于不需要和用户交互的提示消息，上方是图片，下方文字
 */

public class TipsToast {

    private static TextView mTextView;
    private static ImageView mImageView;

    public static void showToast(Context context, String message, int imageResource) {
        //加载Toast布局
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.toast_custom, null);
        //初始化布局控件
        mTextView = (TextView) toastRoot.findViewById(R.id.message);
        mImageView = (ImageView) toastRoot.findViewById(R.id.imageView);
        //为控件设置属性
        mTextView.setText(message);
        mImageView.setImageResource(imageResource);
        //Toast的初始化
        Toast toastStart = new Toast(context);
        //获取屏幕高度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        //Toast的Y坐标是屏幕高度的1/3，不会出现不适配的问题
        toastStart.setGravity(Gravity.TOP, 0, height / 3);
        toastStart.setDuration(Toast.LENGTH_LONG);
        toastStart.setView(toastRoot);
        toastStart.show();
    }
}
