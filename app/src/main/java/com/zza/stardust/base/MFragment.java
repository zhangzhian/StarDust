package com.zza.stardust.base;

import android.os.Bundle;
import android.view.View;

import com.zza.library.base.BaseFragment;


public abstract class MFragment extends BaseFragment {


    @Override
    protected void init(View rootView, Bundle savedInstanceState) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
