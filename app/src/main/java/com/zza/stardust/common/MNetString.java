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
public class MNetString {
    /**
     * 服务器IP
     */
    public static String YD_TFTP_IP = "192.168.225.1";
    /**
     * 服务器端口号
     */
    public static int YD_TFTP_PORT = 69;

    /**
     * 获取服务器地址
     */
    public static String getYD_TFTP_URL() {
        return YD_TFTP_IP + ":" + YD_TFTP_PORT;
    }
}