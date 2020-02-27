package com.zza.stardust.base;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.zza.library.base.BaseFragment;
import com.zza.library.utils.StatusBarUtil;
import com.zza.stardust.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class MFragment extends BaseFragment {

    Unbinder unbinder;
    
    @Override
    protected void onInit(View rootView, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, rootView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
