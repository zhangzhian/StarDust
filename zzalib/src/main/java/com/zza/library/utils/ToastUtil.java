package com.zza.library.utils;

import android.content.Context;
import android.widget.Toast;

import com.zza.library.base.BaseApplication;

/**
 * @Author: 张志安
 * @Mail: zhangzhian2016@gmail.com
 * @Date: 2018/9/26 20:11
 * <p>
 * Toast工具类
 */

public class ToastUtil {
    /**
     * @param msg 打印文本内容
     */
    public static void show(String msg) {
        Toast.makeText(BaseApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param ctx 上下文环境
     * @param msg 打印文本内容
     */
    public static void show(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param msg 打印文本内容
     */
    public static void showLong(String msg) {
        Toast.makeText(BaseApplication.getContext(), msg, Toast.LENGTH_LONG).show();
    }

    /**
     * @param ctx 上下文环境
     * @param msg 打印文本内容
     */
    public static void showLong(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }


}
