package com.zza.stardust.app.ui;

import android.os.Bundle;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zza.library.base.BaseFragment;
import com.zza.library.base.BasePresenter;
import com.zza.library.common.lmpl.OnItemClickListener;
import com.zza.library.weight.TitleLayout;
import com.zza.stardust.R;
import com.zza.stardust.app.adpter.AppAdapter;
import com.zza.stardust.beam.AppInfoBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Author: 张志安
 * @Mail: zhangzhian2016@gmail.com
 * @Date: 2018/10/15 20:33
 */
public class AppFragment extends BaseFragment implements TitleLayout.TitleClickListener, OnItemClickListener {

    @BindView(R.id.rv_app)
    RecyclerView rvApp;
    Unbinder unbinder;
    @BindView(R.id.tl_title)
    TitleLayout tlTitle;

    private AppAdapter appAdapter;
    private List<AppInfoBean> appInfoBeanList;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.fragment_app;
    }

    @Override
    protected void init(View rootView, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, rootView);
        initView(rootView);
        initAppData();
        initRecycleView();

    }

    private void initView(View rootView) {
        tlTitle.setOnTitleClickListener(this);
    }

    private void initRecycleView() {
        //设置布局管理器
        GridLayoutManager manager = new GridLayoutManager(getContext(),3 );

        rvApp.setLayoutManager(manager);
        appAdapter = new AppAdapter(appInfoBeanList, getActivity());
        appAdapter.setOnItemClick(this);
        rvApp.setAdapter(appAdapter);
    }

    private void initAppData() {
        appInfoBeanList = new ArrayList<>();

        AppInfoBean appinfo = new AppInfoBean();
        appinfo.setImage(R.drawable.ic_launcher_foreground);
        appinfo.setName("测试");
        for (int i = 0; i < 4; i++) {
            appInfoBeanList.add(appinfo);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void leftClick(View view) {

    }

    @Override
    public void rightClick(View view) {

    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
