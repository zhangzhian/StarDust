package com.zza.stardust.home.ui;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zza.library.base.BaseActivity;
import com.zza.library.base.BasePresenter;
import com.zza.stardust.R;
import com.zza.stardust.app.ui.AppFragment;
import com.zza.stardust.home.adpter.FragmentAdapter;
import com.zza.stardust.mine.ui.MineFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

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
    protected void init() {
        ButterKnife.bind(this);
        initViewPager();
        initBottomTab(HOME);
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
                tvHome.setTextColor(getResources().getColor(R.color.color_1296db));
                break;
            case APP:
                ivApp.setImageDrawable(getResources().getDrawable(R.drawable.app_press));
                tvApp.setTextColor(getResources().getColor(R.color.color_1296db));
                break;
            case MINE:
                ivMine.setImageDrawable(getResources().getDrawable(R.drawable.mine_press));
                tvMine.setTextColor(getResources().getColor(R.color.color_1296db));
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
                break;
            case R.id.ll_personal_center:
                initBottomTab(MINE);
                vp.setCurrentItem(MINE);
                break;
        }
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
}
