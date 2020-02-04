package com.zza.stardust.app.ui.tboxupgrade.api;

import com.zza.stardust.app.ui.tboxupgrade.upgrade.YodoTBoxUpgrade;
import com.zza.stardust.app.ui.tboxupgrade.api.callback.TransFileCallBack;
import com.zza.stardust.app.ui.tboxupgrade.api.callback.UpgradeCallBack;

public class YodoTBoxSDK {

    private volatile static YodoTBoxSDK uniqueInstance;
    //zt
    //private static final String host = "192.168.100.1";
    private static final String host = "192.168.225.1";
    private static final int port = 69;
    private static final int port_u = 60002;
    private String softVersion = "1";
    private String hardVersion = "1";

    private YodoTBoxSDK() {
    }

    public static YodoTBoxSDK getInstance() {
        if (uniqueInstance == null) {
            synchronized (YodoTBoxSDK.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new YodoTBoxSDK();
                }
            }
        }
        return uniqueInstance;
    }


    public void UpgradeTBox(String filePath, UpgradeCallBack callBack) {
        UpgradeTBoxControl(filePath, callBack);
    }

    public void ForceSetRunning() {
        YodoTBoxUpgrade.getInstance().ForceSetRunning();
    }

    private void UpgradeTBoxControl(final String filePath, final UpgradeCallBack callBack) {
        YodoTBoxUpgrade.getInstance().TransFile(host, port, filePath, new TransFileCallBack() {
            @Override
            public void onTrans(String direction, String packetData, int progress) {
                callBack.onTrans(direction, packetData, progress);
            }

            @Override
            public void onTransSuccess() {
                callBack.onTransSuccess();
                YodoTBoxUpgrade.getInstance().UpgradeTBox(host, port_u, softVersion, hardVersion, callBack);
            }

            @Override
            public void onTransFail(int errorCode, Exception exception) {
                callBack.onTransFail(errorCode, exception);
            }
        });
    }

}
