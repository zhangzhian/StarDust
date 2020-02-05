package com.zza.stardust.app.ui.tboxupgrade.api;

import com.zza.stardust.app.ui.tboxupgrade.upgrade.YodoTBoxUpgradeImpl;
import com.zza.stardust.callback.TransFileCallBack;
import com.zza.stardust.callback.UpgradeCallBack;

public class YodoTBoxSDK {

    private volatile static YodoTBoxSDK uniqueInstance;
    //zt
    //private static final String host = "192.168.100.1";
    //other
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

    public void UpgradeTBox(String host, int port_trans, int port_upgrade, String softVersion, String hardVersion, String filePath, UpgradeCallBack callBack) {
        UpgradeTBoxControl(host, port_trans, port_upgrade, softVersion, hardVersion, filePath, callBack);
    }

    public void ForceSetRunning() {
        YodoTBoxUpgradeImpl.getInstance().ForceSetRunningFalse();
    }

    private void UpgradeTBoxControl(final String filePath, final UpgradeCallBack callBack) {
        YodoTBoxUpgradeImpl.getInstance().TransFile(host, port, filePath, new TransFileCallBack() {
            @Override
            public void onTrans(String direction, String packetData, int progress) {
                callBack.onTrans(direction, packetData, progress);
            }

            @Override
            public void onTransSuccess() {
                callBack.onTransSuccess();
                YodoTBoxUpgradeImpl.getInstance().UpgradeTBox(host, port_u, softVersion, hardVersion, callBack);
            }

            @Override
            public void onTransFail(int errorCode, Exception exception) {
                callBack.onTransFail(errorCode, exception);
            }
        });
    }

    private void UpgradeTBoxControl(final String host, final int port_trans, final int port_upgrade,
                                    final String softVersion, final String hardVersion,
                                    final String filePath, final UpgradeCallBack callBack) {
        YodoTBoxUpgradeImpl.getInstance().TransFile(host, port_trans, filePath, new TransFileCallBack() {
            @Override
            public void onTrans(String direction, String packetData, int progress) {
                callBack.onTrans(direction, packetData, progress);
            }

            @Override
            public void onTransSuccess() {
                callBack.onTransSuccess();
                YodoTBoxUpgradeImpl.getInstance().UpgradeTBox(host, port_upgrade, softVersion, hardVersion, callBack);
            }

            @Override
            public void onTransFail(int errorCode, Exception exception) {
                callBack.onTransFail(errorCode, exception);
            }
        });
    }
}
