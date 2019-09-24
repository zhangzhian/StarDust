package com.zza.stardust.app.ui.TFTP;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zza.stardust.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TBoxLogFragment extends Fragment {


    public TBoxLogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tox_log, container, false);
    }

}
