package com.zza.stardust.callback;

/**
 * @Author: 张志安
 * @Mail: zhangzhian_123@qq.com zhangzhian2016@gmail.com
 * @Description:
 * @CreateDate: 2020/2/4 20:09
 * @UpdateDate: 2020/2/4 20:09
 * @UpdateRemark:
 * @Version: 1.0
 */
public interface PullFileCallBack extends TransFileCallBack {
    void onTransSingleSuccess(String fileName, String filePath);
    void onTransSingleFail(String fileName, String filePath);
}
