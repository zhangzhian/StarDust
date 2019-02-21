package com.zza.library.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.RequiresPermission;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;

/**
 *
 * @Author: 张志安
 *
 * @Mail:   zhangzhian2016@gmail.com
 *
 * @Date: 2018/9/27 19:41
 *
 */
public class NetworkUtil {


    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null) {
                return info.isAvailable();
            }
        }
        return false;
    }
}
