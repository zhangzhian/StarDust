package com.zza.stardust.app.ui.androidhero;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zza.library.base.BasePresenter;
import com.zza.library.common.lmpl.IOnItemClickListener;
import com.zza.stardust.R;
import com.zza.stardust.app.adpter.StrItemAdapter;
import com.zza.stardust.base.MActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AndroidHeroActivity extends MActivity implements IOnItemClickListener {

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
        return R.layout.activity_android_hero;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        super.onInit(savedInstanceState);
        initData();
        initRecycleView();
    }

    private void initData() {
        data.clear();
        data.add("自定义组件");
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
        switch (position){
            case 0:
                startActivity(new Intent(this,CustomViewActivity.class));
                break;

            case 1:

                break;
        }
    }
}
