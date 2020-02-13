package com.zza.stardust.app.ui.tboxprotobuf.control;

import com.zza.stardust.app.ui.tboxprotobuf.IVITboxProto;

/**
 * @Author: 张志安
 * @Mail: zhangzhian_123@qq.com zhangzhian2016@gmail.com
 * @Description:
 * @CreateDate: 2020/2/10 15:04
 * @UpdateDate: 2020/2/10 15:04
 * @UpdateRemark:
 * @Version: 1.0
 */
public interface ProtobufListener {
    void receData(IVITboxProto.TopMessage data);

    void onConnectSuccess();

    void onConnectFail(int code, Exception e);

    void onDisConnect();

    void onDisConnectFail(int code, Exception e);

    void sendDataSuccess(int msgI, boolean period);

    void sendDataFail(int msgId, boolean period, int code, Exception e);
}
