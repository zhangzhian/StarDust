package com.zza.stardust.app.ui.tboxprotobuf.control;

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
    void receData(byte[] data);
    void onConnect();
    void onDisConnect(Exception e,String info);
}
