package com.zza.stardust.common;

/**
 * @Author: 张志安
 * @Description: App的类型相关信息
 * @CreateDate: 2020/2/3 17:10
 * @UpdateDate: 2020/2/3 17:10
 * @UpdateRemark:
 * @Version: 1.0
 */
public final class MAppTypeInfo {

    //APP类型列表
    public static final int APP_ZZA = 0;
    public static final int APP_YD_YODO = 1;
    public static final int APP_YD_ZT = 2;
    public static final int APP_YD_OTHER_CAR_FACTORY = 3;

    //唯一确定APP类型
    //需要在APP编译前确定
    public static final int APP_TYPE = APP_YD_YODO;


}
