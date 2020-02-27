package com.zza.stardust.home.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zza.library.base.BasePresenter;
import com.zza.stardust.R;
import com.zza.stardust.app.ui.AppFragment;
import com.zza.stardust.base.MActivity;
import com.zza.stardust.common.MAppConfigInfo;
import com.zza.stardust.common.MAppInfo;
import com.zza.stardust.common.MAppTypeInfo;
import com.zza.stardust.home.adpter.FragmentAdapter;
import com.zza.stardust.mine.ui.MineFragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MActivity implements ViewPager.OnPageChangeListener {

    private static final int HOME = 0;
    private static final int APP = 1;
    private static final int MINE = 2;

    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.iv_app)
    ImageView ivApp;
    @BindView(R.id.tv_app)
    TextView tvApp;
    @BindView(R.id.ll_app)
    LinearLayout llApp;
    @BindView(R.id.iv_mine)
    ImageView ivMine;
    @BindView(R.id.tv_mine)
    TextView tvMine;
    @BindView(R.id.ll_personal_center)
    LinearLayout llPersonalCenter;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.fl_view)
    FrameLayout flView;
    @BindView(R.id.vp)
    ViewPager vp;
    private AppFragment appFragment;
    private HomeFragment homeFragment;
    private MineFragment mineFragment;


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        super.onInit(savedInstanceState);
        initViewPager();
        initBottomTab(HOME);
        initAppShowData();
    }

    /**
     * 初始化底部Tab
     *
     * @param home
     */
    private void initBottomTab(int home) {
        ivHome.setImageDrawable(getResources().getDrawable(R.drawable.home));
        ivApp.setImageDrawable(getResources().getDrawable(R.drawable.app));
        ivMine.setImageDrawable(getResources().getDrawable(R.drawable.mine));
        tvHome.setTextColor(getResources().getColor(R.color.text333333));
        tvApp.setTextColor(getResources().getColor(R.color.text333333));
        tvMine.setTextColor(getResources().getColor(R.color.text333333));

        switch (home) {
            case HOME:
                ivHome.setImageDrawable(getResources().getDrawable(R.drawable.home_press));
                tvHome.setTextColor(getResources().getColor(R.color.blue_1296db));
                break;
            case APP:
                ivApp.setImageDrawable(getResources().getDrawable(R.drawable.app_press));
                tvApp.setTextColor(getResources().getColor(R.color.blue_1296db));
                break;
            case MINE:
                ivMine.setImageDrawable(getResources().getDrawable(R.drawable.mine_press));
                tvMine.setTextColor(getResources().getColor(R.color.blue_1296db));
                break;
        }
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        homeFragment = new HomeFragment();
        appFragment = new AppFragment();
        mineFragment = new MineFragment();
        //构造适配器
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(homeFragment);
        fragments.add(appFragment);
        fragments.add(mineFragment);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        //设定适配器
        vp.setAdapter(adapter);
        vp.addOnPageChangeListener(this);
    }


    @OnClick({R.id.ll_home, R.id.ll_app, R.id.ll_personal_center})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
                initBottomTab(HOME);
                vp.setCurrentItem(HOME);
                break;
            case R.id.ll_app:
                initBottomTab(APP);
                vp.setCurrentItem(APP);
                //installApp("/storage/extsd/ipc030601.apk");
                break;
            case R.id.ll_personal_center:
                initBottomTab(MINE);
                vp.setCurrentItem(MINE);
                break;
        }
    }

    public  boolean installApp(String apkPath) {
        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder errorMsg = new StringBuilder();
        try {
            process = new ProcessBuilder("pm", "install","-i","com.yodosmart.ipc", "-r", apkPath).start();
            successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
            errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String s;
            while ((s = successResult.readLine()) != null) {
                successMsg.append(s);
            }
            while ((s = errorResult.readLine()) != null) {
                errorMsg.append(s);
            }
        } catch (Exception e) {

        } finally {
            try {
                if (successResult != null) {
                    successResult.close();
                }
                if (errorResult != null) {
                    errorResult.close();
                }
            } catch (Exception e) {

            }
            if (process != null) {
                process.destroy();
            }
        }
        Log.e("zza",""+errorMsg.toString());
        Toast.makeText(this,""+errorMsg.toString()+"  "+successMsg , Toast.LENGTH_LONG).show();
        //如果含有“success”单词则认为安装成功
        return successMsg.toString().equalsIgnoreCase("success");
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        initBottomTab(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }


    private void initAppShowData() {
        MAppConfigInfo.clearFuncList();
        switch (MAppTypeInfo.APP_TYPE) {
            case MAppTypeInfo.APP_ZZA:
                MAppInfo.getDefaultAllAppInfoData();
                break;
            case MAppTypeInfo.APP_YD_YODO:
                MAppInfo.getYodosmartYodoData();
                break;
            case MAppTypeInfo.APP_YD_ZT:
                MAppInfo.getYodosmartZTData();
                break;
            case MAppTypeInfo.APP_YD_OTHER_CAR_FACTORY:
                MAppInfo.getYodosmartUniversalCarfactoryData();
                break;
        }


    }


}
