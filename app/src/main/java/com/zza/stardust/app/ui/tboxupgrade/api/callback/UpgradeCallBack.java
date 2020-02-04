package com.zza.stardust.app.ui.tboxupgrade.api.callback;

public interface UpgradeCallBack {
    void onTrans(String direction, String packetData, int progress);
    void onTransSuccess();
    void onTransFail(int errorCode, Exception exception);
    void onUpgradeStart();
    void onUpgradeSuccess();
    void onUpgradeFail(byte errorCode, Exception e);
}
