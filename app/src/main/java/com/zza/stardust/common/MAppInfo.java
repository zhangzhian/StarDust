package com.zza.stardust.common;

import com.zza.stardust.R;
import com.zza.stardust.bean.AppInfoBean;

/**
 * @Author: 张志安
 * @Mail: zhangzhian_123@qq.com zhangzhian2016@gmail.com
 * @Description:
 * @CreateDate: 2020/2/4 16:04
 * @UpdateDate: 2020/2/4 16:04
 * @UpdateRemark:
 * @Version: 1.0
 */
public class MAppInfo {

    public static final int DEFAULT_ALL_START = 0;
    public static final int APP_NET_OKHTTP = DEFAULT_ALL_START + 1;
    public static final int APP_ANDROID_HERO = DEFAULT_ALL_START + 2;
    public static final int APP_ANDROID_ART = DEFAULT_ALL_START + 3;
    public static final int APP_EVENTBUS = DEFAULT_ALL_START + 4;
    public static final int APP_PERMISSION = DEFAULT_ALL_START + 5;
    public static final int APP_RECYCLERVIEW = DEFAULT_ALL_START + 6;
    public static final int APP_CONSTRAINTLAYOUT = DEFAULT_ALL_START + 7;

    public static final int YD_START = 1000;
    public static final int APP_UPGRADE_WIFI = YD_START + 1;
    public static final int APP_SCREEN_TBOX_UPGRADE = YD_START + 2;
    public static final int APP_LOG_WIFI = YD_START + 3;
    public static final int APP_PROTOBUF_DEBUG = YD_START + 4;

    private static AppInfoBean appInfoOkHttp = new AppInfoBean();
    private static AppInfoBean appConstraintLayout = new AppInfoBean();
    private static AppInfoBean appTBoxUpgrade = new AppInfoBean();
    private static AppInfoBean appTBoxUpgradeDebug = new AppInfoBean();
    private static AppInfoBean appTBoxLog = new AppInfoBean();
    private static AppInfoBean appTBoxProtobufTest = new AppInfoBean();
    private static AppInfoBean appAndroidHero = new AppInfoBean();
    private static AppInfoBean appAndroidArt = new AppInfoBean();
    private static AppInfoBean appEventBus = new AppInfoBean();
    private static AppInfoBean appPermission = new AppInfoBean();
    private static AppInfoBean appRecyclerView = new AppInfoBean();


    static {
        //OkHttp
        appInfoOkHttp.setId(APP_NET_OKHTTP);
        appInfoOkHttp.setImage(R.drawable.app_net_okhttp);
        appInfoOkHttp.setName("OkHttp");

        appAndroidHero.setId(APP_ANDROID_HERO);
        appAndroidHero.setImage(R.drawable.app_android_hero);
        appAndroidHero.setName("群英传");

        appAndroidArt.setId(APP_ANDROID_ART);
        appAndroidArt.setImage(R.drawable.app_android_art);
        appAndroidArt.setName("开发艺术探索");

        appEventBus.setId(APP_EVENTBUS);
        appEventBus.setImage(R.drawable.app_android);
        appEventBus.setName("EventBus");

        appPermission.setId(APP_PERMISSION);
        appPermission.setImage(R.drawable.app_android);
        appPermission.setName("权限");

        appRecyclerView.setId(APP_RECYCLERVIEW);
        appRecyclerView.setImage(R.drawable.app_android);
        appRecyclerView.setName("RecyclerView");


        appConstraintLayout.setId(APP_CONSTRAINTLAYOUT);
        appConstraintLayout.setImage(R.drawable.app_layout);
        appConstraintLayout.setName("ConstraintLayout");

        /**
         * yd app
         * */
        appTBoxUpgrade.setId(APP_UPGRADE_WIFI);
        appTBoxUpgrade.setImage(R.drawable.upgrade_wifi_press);
        appTBoxUpgrade.setName("TBox Wifi升级");

        appTBoxUpgradeDebug.setId(APP_SCREEN_TBOX_UPGRADE);
        appTBoxUpgradeDebug.setImage(R.drawable.app_box_wifi_upgrade);
        appTBoxUpgradeDebug.setName("大屏TBox升级");

        appTBoxLog.setId(APP_LOG_WIFI);
        appTBoxLog.setImage(R.drawable.log_wifi_press);
        appTBoxLog.setName("TBox日志获取");

        appTBoxProtobufTest.setId(APP_PROTOBUF_DEBUG);
        appTBoxProtobufTest.setImage(R.drawable.debug_press);
        appTBoxProtobufTest.setName("Protobuf工具");

    }

    public static void getYodosmartUniversalCarfactoryData() {
        MAppConfigInfo.addFuncList(appTBoxUpgrade);
        MAppConfigInfo.addFuncList(appTBoxLog);

    }

    public static void getYodosmartZTData() {
        MAppConfigInfo.addFuncList(appTBoxUpgrade);
        MAppConfigInfo.addFuncList(appTBoxLog);
    }

    public static void getYodosmartYodoData() {
        MAppConfigInfo.addFuncList(appTBoxUpgrade);
        MAppConfigInfo.addFuncList(appTBoxUpgradeDebug);
        MAppConfigInfo.addFuncList(appTBoxLog);
        MAppConfigInfo.addFuncList(appTBoxProtobufTest);
    }

    public static void getDefaultAllAppInfoData() {
        MAppConfigInfo.addFuncList(appInfoOkHttp);
        MAppConfigInfo.addFuncList(appConstraintLayout);
        MAppConfigInfo.addFuncList(appAndroidHero);
        MAppConfigInfo.addFuncList(appAndroidArt);
        MAppConfigInfo.addFuncList(appEventBus);
        MAppConfigInfo.addFuncList(appPermission);
        MAppConfigInfo.addFuncList(appRecyclerView);
//        MAppConfigInfo.addFuncList(appTBoxUpgrade);
//        MAppConfigInfo.addFuncList(appTBoxUpgradeDebug);
//        MAppConfigInfo.addFuncList(appTBoxLog);
//        MAppConfigInfo.addFuncList(appTBoxProtobufTest);
    }

}
