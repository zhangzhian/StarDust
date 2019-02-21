package com.zza.library.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;

import com.zza.library.R;
import com.zza.library.weight.TitleLayout;


public abstract class BaseActivity<V, T extends BasePresenter<V>> extends FragmentActivity{

    protected T mPresenter;
    private SwipeRefreshLayout mRefreshLayout;
    private boolean mIsRequestDataRefresh = false;
    private TitleLayout title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        ActivityCollector.addActivity(this);

//        setTransparentStatusBar();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            StatusBarUtil.transparencyBar(this);
//            StatusBarUtil.StatusBarLightMode(this);
//        } else {
//            StatusBarUtil.setStatusBarColor(this, R.color.black);
//        }

        //允许为空，不是所有都要实现MVP模式
        if (createPresenter() != null) {
            mPresenter = createPresenter();
            mPresenter.attachView((V) this);
        }
        setContentView(provideContentViewId());//布局

        if (isSetRefresh()) {
            setupSwipeRefresh();
        }
        initTitle();
        init(savedInstanceState);
    }

    public void setTransparentStatusBar() {
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

    }


    private void initTitle() {
        //title = (TitleLayout) findViewById(R.id.tl_title);
//        if (title != null)
//            title.setOnTitleClickListener(this);
    }

    protected TitleLayout getTilte() {
        if (title == null) {
            //title = (TitleLayout) findViewById(R.id.tl_title);
//            if (title != null)
//                title.setOnTitleClickListener(this);
        }
        return title;
    }

    private void setupSwipeRefresh() {
        //mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl);
        if (mRefreshLayout != null) {
            mRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1,
                    R.color.refresh_progress_2, R.color.refresh_progress_3);
            mRefreshLayout.setProgressViewOffset(true, 0, (int) TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
            mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    requestDataRefresh();
                }
            });
        }
    }

    public void requestDataRefresh() {
        mIsRequestDataRefresh = true;
    }


    public void setRefresh(boolean requestDataRefresh) {
        if (mRefreshLayout == null) {
            return;
        }
        if (!requestDataRefresh) {
            mIsRequestDataRefresh = false;
            mRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mRefreshLayout != null) {
                        mRefreshLayout.setRefreshing(false);
                    }
                }
            }, 500);
        } else {
            mRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //ActivityCollector.removeActivity(this);
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }


    /**
     * 判断当前 Activity 是否允许返回
     * 主界面不允许返回，次级界面允许返回
     *
     * @return false
     */
    public boolean canBack() {
        return false;
    }

    /**
     * 判断子Activity是否需要刷新功能
     *
     * @return false
     */
    public Boolean isSetRefresh() {
        return false;
    }

    protected abstract T createPresenter();

    abstract protected int provideContentViewId();//用于引入布局文件

    abstract protected void init(Bundle savedInstanceState);//用于初始化view


}
