package com.zza.stardust.app.ui.tboxupgrade.api.callback;

public interface TransFileCallBack {
    void onTrans(String direction, String packetData, int progress);
    void onTransSuccess();
    void onTransFail(int errorCode, Exception exception);
}
