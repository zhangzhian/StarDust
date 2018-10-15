package com.zza.stardust.app.ui;

import android.view.View;

import com.zza.library.base.BaseFragment;
import com.zza.library.base.BasePresenter;
import com.zza.stardust.R;

/**
 * @Author: 张志安
 * @Mail: zhangzhian2016@gmail.com
 * @Date: 2018/10/15 20:33
 */
public class AppFragment extends BaseFragment {
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.fragment_app;
    }

    @Override
    protected void init(View rootView) {

    }
}
