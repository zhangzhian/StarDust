package com.zza.stardust.mine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zza.library.base.BasePresenter;
import com.zza.library.common.lmpl.IOnItemClickListener;
import com.zza.library.utils.ActivityCollector;
import com.zza.library.utils.SpUtil;
import com.zza.library.weight.AlertChooseDialog;
import com.zza.library.weight.CircleImageView;
import com.zza.stardust.R;
import com.zza.stardust.base.MFragment;
import com.zza.stardust.bean.AppInfoBean;
import com.zza.stardust.mine.adpter.MineListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author: 张志安
 * @Mail: zhangzhian_123@qq.com zhangzhian2016@gmail.com
 * @Description:
 * @CreateDate: 2020/2/26 16:05
 * @UpdateDate: 2020/2/26 16:05
 * @UpdateRemark:
 * @Version: 1.0
 */

public class MineFragment extends MFragment implements IOnItemClickListener{


    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.iv_photo)
    CircleImageView ivPhoto;
    @BindView(R.id.tv_name)
    TextView tvName;
    private MineListAdapter adapter;
    private List<AppInfoBean> funcData = new ArrayList<>();

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
        super.onInit(rootView, savedInstanceState);
        initRecyclerView();
        initFuncData();
    }

    /**
     * 初始化rv
     */
    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);
        adapter = new MineListAdapter(getActivity(), funcData);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

    }

    private void initFuncData() {
        funcData.clear();

        AppInfoBean personal_info = new AppInfoBean();
        personal_info.setName(getResources().getString(R.string.personal_info));
        personal_info.setImage(R.drawable.mine_account);

        AppInfoBean about_us = new AppInfoBean();
        about_us.setName(getResources().getString(R.string.about_us));
        about_us.setImage(R.drawable.mine_about);
        AppInfoBean app_settings = new AppInfoBean();
        app_settings.setName(getResources().getString(R.string.app_settings));
        app_settings.setImage(R.drawable.mine_settting);
        AppInfoBean app_exit = new AppInfoBean();
        app_exit.setName(getResources().getString(R.string.app_exit));
        app_exit.setImage(R.drawable.mine_exit);

        funcData.clear();
        funcData.add(personal_info);
        funcData.add(about_us);
        funcData.add(app_settings);
        funcData.add(app_exit);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0:
                personInfo();
                break;
            case 1:
                about();
                break;
            case 2:
                setting();
                break;
            case 3:
                exitLogin();
                break;
        }
    }



    private void exitLogin() {
        AlertChooseDialog dialog = new AlertChooseDialog(getActivity()) {
            @Override
            public void onRight(View view) {
//                SpUtil.putBoolean(MineActivity.this, "isFirst", true);
////                SpUtil.putString(MineActivity.this, "username", "");
////                SpUtil.putString(MineActivity.this, "id", "");
////                SpUtil.putString(MineActivity.this, "mobile", "");
////                SpUtil.putString(MineActivity.this, "realname", "");
////                SpUtil.remove(MineActivity.this,"validTime");
////                dismiss();
////                ActivityCollector.finishAll();
////                Intent intent = new Intent(MineActivity.this, LoginActivity.class);
////                startActivity(intent);
            }

            @Override
            public void onLeft(View view) {
                dismiss();
            }
        };
        dialog.setButtonNum(3);
        dialog.setTvContent("确定注销？", R.color.text333333, 15.0f);
        dialog.setBtLeftText("取消", R.color.grey_696969, 15.0f);
        dialog.setBtRightText("确定", R.color.blue_1296db, 15.0f);
        dialog.showDialog();
    }

    private void setting() {
//        Intent intent = new Intent(this, SettingActivity.class);
//        startActivity(intent);
    }

    private void about() {
//        Intent intent = new Intent(this,AboutActivity.class);
//        startActivity(intent);
    }

    private void personInfo() {
//        Intent intent = new Intent(this,PersonalActivity.class);
//        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
