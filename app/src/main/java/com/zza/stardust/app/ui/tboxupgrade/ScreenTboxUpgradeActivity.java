package com.zza.stardust.app.ui.tboxupgrade;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zza.library.base.BasePresenter;
import com.zza.library.utils.LogUtil;
import com.zza.library.utils.ToastUtil;
import com.zza.stardust.R;
import com.zza.stardust.app.ui.tboxupgrade.api.YodoTBoxSDK;
import com.zza.stardust.callback.UpgradeCallBack;
import com.zza.stardust.base.MActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ScreenTboxUpgradeActivity extends MActivity {

    private static final int CODE_FOR_WRITE_PERMISSION = 1;
    @BindView(R.id.bt_upgrade)
    Button btUpgrade;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.tv_log_info)
    TextView tvLogInfo;

    private StringBuffer bufferShow = new StringBuffer();
    private boolean isPermission = false;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_screen_tbox_upgrade;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        super.onInit(savedInstanceState);
        //使用兼容库就无需判断系统版本
        int hasWriteStoragePermission = ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED) {
            //拥有权限，执行操作
            isPermission = true;
        } else {
            //没有权限，向用户请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_FOR_WRITE_PERMISSION);
        }
        clearlog();
    }

    @OnClick({R.id.bt_upgrade})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_upgrade:
                if (!isPermission) {
                    ToastUtil.show("无读写权限，请授予权限后重试");
                } else {
                    showlog("click bottom");
                    upgrade();
                }
                break;
        }
    }

    private void upgrade() {
        YodoTBoxSDK sdk = YodoTBoxSDK.getInstance();
        sdk.UpgradeTBox(Environment.getExternalStorageDirectory().getPath() + "/TBoxData/TBOX.bin", new UpgradeCallBack() {
            @Override
            public void onTrans(String direction, String packetData, int progress) {
                LogUtil.i(direction + " " + packetData + " progress:" + progress);
            }

            @Override
            public void onTransSuccess() {
                showlog("onTransSuccess");
            }

            @Override
            public void onTransFail(int i, Exception e) {
                showlog("onTransFinsh:" + i + " " + e.getMessage());
            }

            @Override
            public void onUpgradeStart() {
                showlog("onUpgradeStart");
            }

            @Override
            public void onUpgradeSuccess() {
                showlog("onUpgradeSuccess");
            }

            @Override
            public void onUpgradeFail(byte b, Exception e) {
                showlog("onUpgradeFail:" + b + " " + e.getMessage());
            }
        });
    }

    private void showlog(String log){
        bufferShow.append(log + "\r\n");
        runOnUiThread(() -> {
            tvLogInfo.setText(bufferShow.toString());
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
        });

    }

    private void clearlog(){
        bufferShow.setLength(0);
        runOnUiThread(() -> {
            tvLogInfo.setText(bufferShow.toString());
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
        });
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
}
