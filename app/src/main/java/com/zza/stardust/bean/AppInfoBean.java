package com.zza.stardust.bean;

import android.support.annotation.DrawableRes;

/**
  * @Author:         张志安
  * @Mail:           zhangzhian_123@qq.com zhangzhian2016@gmail.com
  * @Description:
  * @CreateDate:     2020/2/4 16:08
  * @UpdateDate:     2020/2/4 16:08
  * @UpdateRemark:
  * @Version:        1.0
 */

public class AppInfoBean {
    private int id;
    private String name;
    @DrawableRes
    private int image;

    public AppInfoBean() {

    }

    public AppInfoBean(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public AppInfoBean(int id, String name, int image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


}
