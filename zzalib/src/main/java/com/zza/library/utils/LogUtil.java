package com.zza.library.utils;

import android.util.Log;

/**
 * @Author: 张志安
 * @Mail: zhangzhian2016@gmail.com
 * @Date: 2018/9/26 19:18
 *
 * 定制自己的日志工具
 * 打印一行DEBUG级别的日志可以写成LogUtil.d("123")
 * 只需要修改LEVEL的值就可以自由控制打印行为
 */


public class LogUtil {

    public static final int VERBOSE = 1;

    public static final int DEBUG = 2;

    public static final int INFO = 3;

    public static final int WARN = 4;

    public static final int ERROR = 5;

    public static final int NOTHING = 6;

    //LEVEL 和 TAG 可以在baseApplication中进行设置
    //当前log显示等级【1-6】，默认全部显示 1全部显示 6为不显示 超过6全部显示
    public static int LEVEL = VERBOSE;
    //当前log名称
    public static String TAG = "Tag";


    public static void v(String tag, String msg) {
        if (LEVEL <= VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (LEVEL <= DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (LEVEL <= INFO) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LEVEL <= WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LEVEL <= ERROR) {
            Log.e(tag, msg);
        }
    }


    public static void v(String msg) {
        if (LEVEL <= VERBOSE) {
            Log.v(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (LEVEL <= DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (LEVEL <= INFO) {
            Log.i(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (LEVEL <= WARN) {
            Log.w(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (LEVEL <= ERROR) {
            Log.e(TAG, msg);
        }
    }


    public static int getLEVEL() {
        return LEVEL;
    }

    public static void setLEVEL(int leave) {
        if (leave >= NOTHING) leave = VERBOSE;
        LogUtil.LEVEL = leave;
    }

    public static String getTAG() {
        return TAG;
    }

    public static void setTAG(String tag) {
        LogUtil.TAG = tag;
    }
}