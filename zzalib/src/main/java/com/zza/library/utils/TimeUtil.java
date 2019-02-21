package com.zza.library.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: 张志安
 * @Mail: zhangzhian2016@gmail.com
 * @Date: 2018/9/26 20:31
 *
 * 时间相关的工具类
 */
public class TimeUtil {

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(long s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(s);
        return simpleDateFormat.format(date);
    }

    /*
     * 将时间戳转换为时间 -- 年
     */
    public static String stampToDateYear(long s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date(s);
        return simpleDateFormat.format(date);
    }

    /*
     * 将时间戳转换为时间 -- 月
     */
    public static String stampToDateMouth(long s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        Date date = new Date(s);
        return simpleDateFormat.format(date);
    }

    /*
     *两个时间戳转化为间隔
     */

    public static String stamp2distance(long time) {
        long currentTime = System.currentTimeMillis() / 1000;
        long distance = Math.abs(currentTime - time);
        if (distance > 0 && distance < 60)
            return distance + "秒前";
        if (distance >= 60 && distance < 3600)
            return Integer.parseInt(distance / 60 + "") + "分钟前";
        if (distance >= 3600 && distance < 3600 * 24)
            return Integer.parseInt(distance / 3600 + "") + "小时前";
        if (distance >= 3600 * 24) return Integer.parseInt(distance / (3600 * 24) + "") + "天前";
        return "";
    }


}
