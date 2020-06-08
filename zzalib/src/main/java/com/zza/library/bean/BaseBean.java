package com.zza.library.bean;

import android.support.annotation.Nullable;


public class BaseBean<T> {

    private String code;
    private String msg;
    @Nullable
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getT() {
        return data;
    }

    public void setT(T t) {
        this.data = t;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
