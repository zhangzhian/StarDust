package com.zza.stardust.app.ui.androidart.binderpool;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.zza.library.base.BasePresenter;
import com.zza.library.utils.LogUtil;
import com.zza.stardust.R;
import com.zza.stardust.base.MActivity;

public class BinderPoolActivity extends MActivity {

    private ISecurityCenter mSecurityCenter;
    private ICompute mCompute;

    @Override
    protected void onInit(Bundle savedInstanceState) {
        super.onInit(savedInstanceState);
        new Thread(() -> doWork()).start();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_binder_pool;
    }

    private void doWork() {
        BinderPool binderPool = BinderPool.getInsance(BinderPoolActivity.this);
        IBinder securityBinder = binderPool
                .queryBinder(BinderPool.BINDER_SECURITY_CENTER);
        mSecurityCenter = (ISecurityCenter) SecurityCenterImpl
                .asInterface(securityBinder);
        LogUtil.d("visit ISecurityCenter");
        String msg = "helloworld-安卓";
        LogUtil.d("content:" + msg);
        try {
            String password = mSecurityCenter.encrypt(msg);
            LogUtil.d("encrypt:" + password);
            LogUtil.d("decrypt:" + mSecurityCenter.decrypt(password));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        LogUtil.d("visit ICompute");
        IBinder computeBinder = binderPool
                .queryBinder(BinderPool.BINDER_COMPUTE);
        mCompute = ComputeImpl.asInterface(computeBinder);
        try {
            LogUtil.d("3+5=" + mCompute.add(3, 5));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
