package com.zza.stardust.app.ui.androidart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zza.library.base.BasePresenter;
import com.zza.library.common.lmpl.IOnItemClickListener;
import com.zza.library.utils.ToastUtil;
import com.zza.stardust.R;
import com.zza.stardust.app.adpter.AppAdapter;
import com.zza.stardust.app.adpter.StrItemAdapter;
import com.zza.stardust.app.ui.androidart.SerializableParcelable.SerializableParcelableActivity1;
import com.zza.stardust.app.ui.androidart.aidl.BookManagerActivity;
import com.zza.stardust.app.ui.androidart.messenger.MessengerActivity;
import com.zza.stardust.app.ui.androidart.provider.ProviderActivity;
import com.zza.stardust.base.MActivity;
import com.zza.stardust.bean.AppInfoBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AndroidArtActivity extends MActivity implements IOnItemClickListener {

    @BindView(R.id.rv_test)
    RecyclerView rvTest;
    private List<String> data = new ArrayList<>();
    private StrItemAdapter adapter = null;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_android_art;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        super.onInit(savedInstanceState);
        initData();
        initRecycleView();
    }

    private void initData() {
        data.clear();
        data.add("Serializable Parcelable");
        data.add("Messenger");
        data.add("AIDL");
        data.add("Provider");
    }

    private void initRecycleView() {
        //设置布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this);

        rvTest.setLayoutManager(manager);
        adapter = new StrItemAdapter(data, this);
        adapter.setOnItemClick(this);
        rvTest.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(AndroidArtActivity.this, SerializableParcelableActivity1.class));
                break;
            case 1:
                startActivity(new Intent(AndroidArtActivity.this, MessengerActivity.class));
                break;
            case 2:
                startActivity(new Intent(AndroidArtActivity.this, BookManagerActivity.class));
                break;
            case 3:
                startActivity(new Intent(AndroidArtActivity.this, ProviderActivity.class));
                break;
        }
    }
}
