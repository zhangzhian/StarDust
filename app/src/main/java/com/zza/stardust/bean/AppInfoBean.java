package com.zza.stardust.bean;

import android.support.annotation.DrawableRes;

/**
 * Author：张志安
 * Date:   2017/9/20 18:56
 * Mail：  zhangzhian2016@gmail.com
 */
public class AppInfoBean {
    private String id;
    private String name;
    @DrawableRes
    private int image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
