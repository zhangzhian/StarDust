package com.zza.stardust.app.ui.tboxprotobuf.control;

/**
 * @Author: 张志安
 * @Mail: zhangzhian_123@qq.com zhangzhian2016@gmail.com
 * @Description:
 * @CreateDate: 2020/2/10 14:16
 * @UpdateDate: 2020/2/10 14:16
 * @UpdateRemark:
 * @Version: 1.0
 */
public interface ProtobufControl {
    void connect(String host, int post);
    void disConnect();
    void sendCmd(int msgId, String cmd,int times, int period);
    void stopCmd(int msgId);
    void addReceDataListener(ProtobufReceDataListener listener);
    void removeReceDataListener(ProtobufReceDataListener listener);
}
