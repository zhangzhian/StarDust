package com.zza.stardust.app.ui.TFTP;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zza.library.base.BasePresenter;
import com.zza.library.utils.FileChooseUtil;
import com.zza.library.utils.LogUtil;
import com.zza.stardust.R;
import com.zza.stardust.base.MActivity;
import com.zza.stardust.home.adpter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zza.stardust.app.ui.TFTP.TBoxUpgradeFragment.REQUEST_CHOOSEFILE;

public class TFTPActivity extends MActivity implements ViewPager.OnPageChangeListener {

    private static final int CODE_FOR_WRITE_PERMISSION = 1;
    @BindView(R.id.fl_view)
    FrameLayout flView;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.iv_upgrade)
    ImageView ivUpgrade;
    @BindView(R.id.tv_upgrade)
    TextView tvUpgrade;
    @BindView(R.id.tv_log)
    TextView tvLog;
    @BindView(R.id.iv_log)
    ImageView ivLog;
    @BindView(R.id.fl_upgrade)
    FrameLayout flUpgrade;
    @BindView(R.id.fl_log)
    FrameLayout flLog;
    private boolean isPermission = false;
    private TBoxUpgradeFragment tBoxUpgradeFragment;
    private TBoxLogFragment tBoxLogFragment;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_tftp;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        super.onInit(savedInstanceState);
        LogUtil.i(Environment.getExternalStorageDirectory().getPath() + "");

        initViewPager();
        initBottomTab(0);

        //使用兼容库就无需判断系统版本
        int hasWriteStoragePermission = ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED) {
            //拥有权限，执行操作
            isPermission = true;
        } else {
            //没有权限，向用户请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_FOR_WRITE_PERMISSION);
        }
    }


    /**
     * 初始化底部Tab
     **/
    private void initBottomTab(int pos) {
        ivUpgrade.setImageDrawable(getResources().getDrawable(R.drawable.upgrade_wifi));
        ivLog.setImageDrawable(getResources().getDrawable(R.drawable.log_wifi));
        tvUpgrade.setTextColor(getResources().getColor(R.color.text333333));
        tvLog.setTextColor(getResources().getColor(R.color.text333333));

        switch (pos) {
            case 0:
                ivUpgrade.setImageDrawable(getResources().getDrawable(R.drawable.upgrade_wifi_press));
                tvUpgrade.setTextColor(getResources().getColor(R.color.color_1296db));
                break;
            case 1:
                ivLog.setImageDrawable(getResources().getDrawable(R.drawable.log_wifi_press));
                tvLog.setTextColor(getResources().getColor(R.color.color_1296db));
                break;

        }
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        tBoxUpgradeFragment = new TBoxUpgradeFragment();
        tBoxLogFragment = new TBoxLogFragment();
        //构造适配器
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(tBoxUpgradeFragment);
        fragments.add(tBoxLogFragment);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        //设定适配器
        vp.setAdapter(adapter);
        vp.addOnPageChangeListener(this);
    }

    public Boolean getPermission() {
        return isPermission;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //通过requestCode来识别是否同一个请求
        if (requestCode == CODE_FOR_WRITE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //用户同意，执行操作
                isPermission = true;
            } else {
                //用户不同意，向用户展示该权限作用
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    new AlertDialog.Builder(this)
                            .setMessage("需要该权限读写本地文件")
                            .setPositiveButton("OK", (dialog1, which) ->
                                    ActivityCompat.requestPermissions(this,
                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                            CODE_FOR_WRITE_PERMISSION))
                            .setNegativeButton("Cancel", null)
                            .create()
                            .show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//选择文件返回
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CHOOSEFILE:
                    String path;
                    Uri uri = data.getData();
                    path = FileChooseUtil.getInstance(this).getChooseFileResultPath(uri);

                    LogUtil.i("File path:" + path);
                    break;
            }
        }
    }


    @OnClick({R.id.iv_upgrade, R.id.tv_upgrade, R.id.tv_log, R.id.iv_log, R.id.fl_upgrade, R.id.fl_log})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_upgrade:
                initBottomTab(0);
                vp.setCurrentItem(0);
                break;
            case R.id.fl_log:
                initBottomTab(1);
                vp.setCurrentItem(1);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

}
