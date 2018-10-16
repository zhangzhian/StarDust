package com.zza.stardust.app.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zza.library.base.BaseFragment;
import com.zza.library.base.BasePresenter;
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
public class AppFragment extends BaseFragment {

    @BindView(R.id.rv_app)
    RecyclerView rvApp;
    Unbinder unbinder;
//    @BindView(R.id.tl_title)
    TitleLayout tlTitle;
    Unbinder unbinder1;

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
    protected void init(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        initView(rootView);
        initAppData();
        initRecycleView();

    }

    private void initView(View rootView) {
        //tlTitle =rootView.findViewById(R.id.tl_title);
        //tlTitle.setTitle("123");
    }

    private void initRecycleView() {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvApp.setLayoutManager(linearLayoutManager);
        appAdapter = new AppAdapter(appInfoBeanList, getActivity());
        rvApp.setAdapter(appAdapter);
    }

    private void initAppData() {
        appInfoBeanList = new ArrayList<>();

        AppInfoBean appinfo = new AppInfoBean();
        appinfo.setName("上传和下载");
        appInfoBeanList.add(appinfo);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
