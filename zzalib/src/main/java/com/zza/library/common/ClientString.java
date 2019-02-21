package com.zza.library.common;

/**
 * 网络相关常量
 *
 */
public class ClientString {
    /**
     * 服务器IP
     */
    public static String BASE_IP = "";
    /**
     * 服务器端口号
     */
    public static String BASE_PORT = "80";

    /**
     * 获取服务器地址
     */
    public static String getBASE_URL() {
        return "http://" + BASE_IP + ":" + BASE_PORT;
    }

    /**
     * 获取服务器图片地址
     */
    public static String getPicBASE_URL() {
        return "http://" + BASE_IP + ":" + BASE_PORT + "/live";
    }


}

