package com.zza.stardust.mine.ui;

import android.os.Bundle;
import android.view.View;

import com.zza.library.base.BaseFragment;
import com.zza.library.base.BasePresenter;
import com.zza.stardust.R;

/**
 * @Author: 张志安
 * @Mail: zhangzhian2016@gmail.com
 * @Date: 2018/10/15 20:33
 */
public class MineFragment extends BaseFragment {
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void onInit(View rootView, Bundle savedInstanceState) {

    }


}
