package com.zza.stardust.app.ui.androidhero;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zza.library.base.BasePresenter;
import com.zza.library.common.lmpl.IOnItemClickListener;
import com.zza.library.utils.ActivityCollector;
import com.zza.library.utils.StatusBarUtil;
import com.zza.stardust.R;
import com.zza.stardust.app.adpter.StrItemAdapter;
import com.zza.stardust.app.ui.MDAnimActivity;
import com.zza.stardust.app.ui.ToolBarActivity;
import com.zza.stardust.app.ui.androidhero.view.XfermodeView;
import com.zza.stardust.base.MActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        data.add("自定义View");
        data.add("自定义ViewGroup");
        data.add("View Scoll分析");
        data.add("ViewGroup Scoll分析");
        data.add("2D绘图基础");
        data.add("XML绘图");
        data.add("绘图机制与技巧");
        data.add("色彩特效处理1");
        data.add("色彩特效处理2");
        data.add("图像特效处理：变换矩阵");
        data.add("图像特效处理：像素块");
        data.add("画笔特效处理：PorterDuffXfermode");
        data.add("画笔特效处理：Shader");
        data.add("画笔特效处理：PathEffect");
        data.add("SurfaceView ");
        data.add("视图动画 ");
        data.add("属性动画 ");
        data.add("自定义动画 ");
        data.add("SVG动画 ");
        data.add("系统信息");
        data.add("PackageManager");
        data.add("ActivityManagerActivity");
        data.add("Palette");
        data.add("ShadowTintingClippingCardView");
        data.add("过渡动画");
        data.add("MD动画效果");
        data.add("ToolBar");
        data.add("Notification");

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
                startActivity(new Intent(this,CustomViewGroupActivity.class));
                break;
            case 2:
                startActivity(new Intent(this,ScollViewActivity.class));
                break;
            case 3:
                startActivity(new Intent(this,ScollViewGroupActivity.class));
                break;
            case 4:
                startActivity(new Intent(this,Base2DPicActivity.class));
                break;
            case 5:
                startActivity(new Intent(this,XMLPicActivity.class));
                break;
            case 6:
                startActivity(new Intent(this,DrawingSkillsActivity.class));
                break;
            case 7:
                startActivity(new Intent(this,ColorMatrix1Activity.class));
                break;
            case 8:
                startActivity(new Intent(this,ColorMatrix2Activity.class));
                break;
            case 9:
                startActivity(new Intent(this,ImageMatrixActivity.class));
                break;
            case 10:
                startActivity(new Intent(this,FlagBitmapActivity.class));
                break;
            case 11:
                startActivity(new Intent(this, XfermodeActivity.class));
                break;
            case 12:
                startActivity(new Intent(this, ShaderActivity.class));
                break;
            case 13:
                startActivity(new Intent(this, PathEffectViewActivity.class));
                break;
            case 14:
                startActivity(new Intent(this, SufaceViewActivity.class));
                break;
            case 15:
                startActivity(new Intent(this, ViewAnimActivity.class));
                break;
            case 16:
                startActivity(new Intent(this, PropertyAnimActivity.class));
                break;
            case 17:
                startActivity(new Intent(this, CustomAnimActivity.class));
                break;
            case 18:
                startActivity(new Intent(this, SVGActivity.class));
                break;
            case 19:
                startActivity(new Intent(this, SystemInfoActivity.class));
                break;
            case 20:
                startActivity(new Intent(this, PackageManagerActivity.class));
                break;
            case 21:
                startActivity(new Intent(this, ActivityManagerActivity.class));
                break;
            case 22:
                startActivity(new Intent(this, PaletteActivity.class));
                break;
            case 23:
                startActivity(new Intent(this, ShadowTintingClippingActivity.class));
                break;
            case 24:
                startActivity(new Intent(this, OverAnimationActivity.class));
                break;
            case 25:
                startActivity(new Intent(this, MDAnimActivity.class));
                break;
            case 26:
                startActivity(new Intent(this, ToolBarActivity.class));
                break;
            case 27:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
        }
    }
}
