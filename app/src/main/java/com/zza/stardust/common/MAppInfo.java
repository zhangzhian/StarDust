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

    private static final int DEFAULT_ALL_START = 0;
    private static final int YD_START = 1000;



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
        appInfoOkHttp.setId(DEFAULT_ALL_START + 1);
        appInfoOkHttp.setImage(R.drawable.app_net_okhttp);
        appInfoOkHttp.setName("OkHttp");

        appInfoOkHttp.setId(DEFAULT_ALL_START + 2);
        appAndroidHero.setImage(R.drawable.app_android_hero);
        appAndroidHero.setName("群英传");

        appInfoOkHttp.setId(DEFAULT_ALL_START + 3);
        appAndroidArt.setImage(R.drawable.app_android_art);
        appAndroidArt.setName("开发艺术探索");

        appInfoOkHttp.setId(DEFAULT_ALL_START + 4);
        appEventBus.setImage(R.drawable.app_android);
        appEventBus.setName("EventBus");

        appInfoOkHttp.setId(DEFAULT_ALL_START + 5);
        appPermission.setImage(R.drawable.app_android);
        appPermission.setName("权限");

        appInfoOkHttp.setId(DEFAULT_ALL_START + 6);
        appRecyclerView.setImage(R.drawable.app_android);
        appRecyclerView.setName("RecyclerView");


        appInfoOkHttp.setId(DEFAULT_ALL_START + 7);
        appConstraintLayout.setImage(R.drawable.app_layout);
        appConstraintLayout.setName("ConstraintLayout");

        /**
         * yd app
         * */
        appInfoOkHttp.setId(YD_START + 1);
        appTBoxUpgrade.setImage(R.drawable.upgrade_wifi_press);
        appTBoxUpgrade.setName("TBox Wifi升级");

        appInfoOkHttp.setId(YD_START + 2);
        appTBoxUpgradeDebug.setImage(R.drawable.app_box_wifi_upgrade);
        appTBoxUpgradeDebug.setName("大屏TBox升级");

        appInfoOkHttp.setId(YD_START + 3);
        appTBoxLog.setImage(R.drawable.log_wifi_press);
        appTBoxLog.setName("TBox日志获取");

        appInfoOkHttp.setId(YD_START + 4);
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
        MAppConfigInfo.addFuncList(appTBoxUpgrade);
        MAppConfigInfo.addFuncList(appTBoxUpgradeDebug);
        MAppConfigInfo.addFuncList(appTBoxLog);
        MAppConfigInfo.addFuncList(appTBoxProtobufTest);
    }

}
