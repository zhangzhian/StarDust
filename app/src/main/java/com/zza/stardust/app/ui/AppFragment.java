package com.zza.stardust.app.ui;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zza.library.base.BaseFragment;
import com.zza.library.base.BasePresenter;
import com.zza.library.common.lmpl.IOnItemClickListener;
import com.zza.library.weight.TitleLayout;
import com.zza.stardust.R;
import com.zza.stardust.app.adpter.AppAdapter;
import com.zza.stardust.app.ui.ConstraintLayout.ConstraintLayoutActivity;
import com.zza.stardust.app.ui.OkHttp.OkHttpActivity;
import com.zza.stardust.bean.AppInfoBean;

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
public class AppFragment extends BaseFragment implements TitleLayout.onTitleClickListener, IOnItemClickListener {

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
        GridLayoutManager manager = new GridLayoutManager(getContext(), 3);

        rvApp.setLayoutManager(manager);
        appAdapter = new AppAdapter(appInfoBeanList, getActivity());
        appAdapter.setOnItemClick(this);
        rvApp.setAdapter(appAdapter);
    }

    private void initAppData() {
        appInfoBeanList = new ArrayList<>();

        //OkHttp
        AppInfoBean appInfoOkHttp = new AppInfoBean();
        appInfoOkHttp.setImage(R.drawable.app_net_okhttp);
        appInfoOkHttp.setName("OkHttp");
        appInfoBeanList.add(appInfoOkHttp);
        AppInfoBean appConstraintLayout = new AppInfoBean();
        appConstraintLayout.setImage(R.drawable.app_layout);
        appConstraintLayout.setName("ConstraintLayout");
        appInfoBeanList.add(appConstraintLayout);
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
        switch (position) {
            case 0:
                getActivity().startActivity(new Intent(getActivity(), OkHttpActivity.class));
                break;
            case 1:
                getActivity().startActivity(new Intent(getActivity(), ConstraintLayoutActivity.class));
                break;
            default:
                break;
        }
    }
}
