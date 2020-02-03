package com.zza.stardust.common;

import com.zza.stardust.bean.AppInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 张志安
 * @Description: APP应用页面具体的配置信息
 * @CreateDate: 2020/2/3 17:17
 * @UpdateDate: 2020/2/3 17:17
 * @UpdateRemark:
 * @Version: 1.0
 */
public final class MAppConfigInfo {
    //保存APP的应用页面的功能列表
    private static List<AppInfoBean> funcList = new ArrayList<>();

    //获取功能列表
    public static List<AppInfoBean> getFuncList() {
        return funcList;
    }

    //设置功能列表
    public static void setFuncList(List<AppInfoBean> funcList) {
        MAppConfigInfo.funcList.clear();
        MAppConfigInfo.funcList.addAll(funcList);
    }

    //添加功能列表
    public static void addFuncList(AppInfoBean func) {
        MAppConfigInfo.funcList.add(func);
    }

    //获取功能列表大小
    public static int getFuncListSize() {
        return funcList.size();
    }

    //获取功能列表大小
    public static void clearFuncList() {
        funcList.clear();
    }
}
