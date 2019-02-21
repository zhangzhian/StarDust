package com.zza.library.base;


import com.zza.library.api.ApiFactory;
import com.zza.library.api.RepairApi;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public abstract class BasePresenter<V> {

    protected Reference<V> mViewRef;

    public static final RepairApi repairApi = ApiFactory.getRepairApiSingleton();

    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    protected V getView() {
        return mViewRef.get();
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}