package com.zza.stardust.common;

/**
  * @Author:         张志安
  * @Mail:           zhangzhian_123@qq.com zhangzhian2016@gmail.com
  * @Description:    网络相关的一些常量
  * @CreateDate:     2020/2/4 19:29
  * @UpdateDate:     2020/2/4 19:29
  * @UpdateRemark:
  * @Version:        1.0
 */
public class MNetInfo {
    /**
     * 服务器IP
     */
    public static String YD_TBOX_IP = "192.168.225.1";
    /**
     * TFTP服务器端口号
     */
    public static int YD_TFTP_PORT = 69;
    /**
     * Protobuf服务器端口号
     */
    public static int YD_PROTOBUF_PORT = 60000;
    /**
     * TFTP获取服务器地址
     */
    public static String getYD_TFTP_URL() {
        return YD_TBOX_IP + ":" + YD_TFTP_PORT;
    }

    /**
     * Protobuf获取服务器地址
     */
    public static String getPROTOBUF_TFTP_URL() {
        return YD_TBOX_IP + ":" + YD_PROTOBUF_PORT;
    }
}