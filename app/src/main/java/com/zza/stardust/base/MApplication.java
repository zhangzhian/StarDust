package com.zza.stardust.base;

import android.support.multidex.MultiDex;

import com.zza.library.base.BaseApplication;
import com.zza.library.utils.LogUtil;
import com.zza.library.utils.VersionUtil;
import com.zza.stardust.app.ui.androidart.crash.CrashHandler;

/**
 * @Author: 张志安
 * @Mail: zhangzhian2016@gmail.com
 * @Date: 2018/10/11 21:21
 */
public class MApplication extends BaseApplication {

    public static String softwareSourceVersionCode;
    public static String softwareSourceVersionName;


    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        LogUtil.setTAG("zza");
        //在这里为应用设置异常处理程序，然后我们的程序才能捕获未处理的异常
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
        softwareSourceVersionCode = VersionUtil.getLocalVersion(context) + "";
        softwareSourceVersionName = VersionUtil.getLocalVersionName(context) + "";
    }
}
