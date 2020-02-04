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
import com.zza.stardust.app.ui.constraintLayout.ConstraintLayoutActivity;
import com.zza.stardust.app.ui.okHttp.OkHttpActivity;
import com.zza.stardust.app.ui.androidart.AndroidArtActivity;
import com.zza.stardust.app.ui.androidhero.AndroidHeroActivity;
import com.zza.stardust.app.ui.tboxlog.GetLogActivity;
import com.zza.stardust.app.ui.tboxprotobuf.ProtobufActivity;
import com.zza.stardust.app.ui.tboxupgrade.ScreenTboxUpgradeActivity;
import com.zza.stardust.app.ui.tboxupgrade.UpgradeWifiActivity;
import com.zza.stardust.common.MAppConfigInfo;
import com.zza.stardust.common.MAppInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Author: 张志安
 * @Mail: zhangzhian_123@qq.com zhangzhian2016@gmail.com
 * @Description:
 * @CreateDate: 2018/10/15 20:33
 * @UpdateDate: 2020/2/3 17:59
 * @UpdateRemark:
 * @Version: 1.0
 */
public class AppFragment extends BaseFragment implements TitleLayout.onTitleClickListener, IOnItemClickListener {

    @BindView(R.id.rv_app)
    RecyclerView rvApp;
    Unbinder unbinder;
    @BindView(R.id.tl_title)
    TitleLayout tlTitle;

    private AppAdapter appAdapter;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.fragment_app;
    }

    @Override
    protected void onInit(View rootView, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, rootView);
        initView(rootView);
        initRecycleView();

    }

    private void initView(View rootView) {
        tlTitle.setOnTitleClickListener(this);
    }

    private void initRecycleView() {
        //设置布局管理器
        GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        //LinearLayoutManager manager = new LinearLayoutManager(getContext());

        rvApp.setLayoutManager(manager);
        appAdapter = new AppAdapter(MAppConfigInfo.getFuncList(), getActivity());
        appAdapter.setOnItemClick(this);
        rvApp.setAdapter(appAdapter);
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
        int id = MAppConfigInfo.getFuncList().get(position).getId();
        switch (id) {
            case MAppInfo.APP_NET_OKHTTP:
                getActivity().startActivity(new Intent(getActivity(), OkHttpActivity.class));
                break;
            case MAppInfo.APP_ANDROID_HERO:
                getActivity().startActivity(new Intent(getActivity(), AndroidHeroActivity.class));
                break;
            case MAppInfo.APP_ANDROID_ART:
                getActivity().startActivity(new Intent(getActivity(), AndroidArtActivity.class));
                break;
            case MAppInfo.APP_EVENTBUS:
                //getActivity().startActivity(new Intent(getActivity(), ConstraintLayoutActivity.class));
                break;
            case MAppInfo.APP_PERMISSION:
                //getActivity().startActivity(new Intent(getActivity(), ConstraintLayoutActivity.class));
                break;
            case MAppInfo.APP_RECYCLERVIEW:
                //getActivity().startActivity(new Intent(getActivity(), ConstraintLayoutActivity.class));
                break;
            case MAppInfo.APP_CONSTRAINTLAYOUT:
                getActivity().startActivity(new Intent(getActivity(), ConstraintLayoutActivity.class));
                break;
            case MAppInfo.APP_UPGRADE_WIFI:
                getActivity().startActivity(new Intent(getActivity(), UpgradeWifiActivity.class));
                break;
            case MAppInfo.APP_SCREEN_TBOX_UPGRADE:
                getActivity().startActivity(new Intent(getActivity(), ScreenTboxUpgradeActivity.class));
                break;
            case MAppInfo.APP_LOG_WIFI:
                getActivity().startActivity(new Intent(getActivity(), GetLogActivity.class));
                break;
            case MAppInfo.APP_PROTOBUF_DEBUG:
                getActivity().startActivity(new Intent(getActivity(), ProtobufActivity.class));
                break;
            default:
                break;
        }
    }
}
