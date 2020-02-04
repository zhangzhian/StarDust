package com.zza.stardust.callback;

public interface TransFileCallBack {
    void onTrans(String direction, String packetData, int progress);
    void onTransSuccess();
    void onTransFail(int errorCode, Exception exception);
}
