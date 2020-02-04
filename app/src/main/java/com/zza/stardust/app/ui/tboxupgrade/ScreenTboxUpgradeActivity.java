package com.zza.stardust.app.ui.tboxupgrade;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zza.library.base.BasePresenter;
import com.zza.library.utils.LogUtil;
import com.zza.stardust.R;
import com.zza.stardust.app.ui.tboxupgrade.api.YodoTBoxSDK;
import com.zza.stardust.callback.UpgradeCallBack;
import com.zza.stardust.base.MActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ScreenTboxUpgradeActivity extends MActivity {

    @BindView(R.id.bt_upgrade)
    Button btUpgrade;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.tv_log_info)
    TextView tvLogInfo;

    private StringBuffer bufferShow = new StringBuffer();

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
        clearlog();
    }

    @OnClick({R.id.bt_upgrade})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_upgrade:
                showlog("click bottom");
                upgrade();
                break;
        }
    }

    private void upgrade() {
        YodoTBoxSDK sdk = YodoTBoxSDK.getInstance();
        sdk.UpgradeTBox(Environment.getExternalStorageDirectory().getPath() + "/TBoxData/TBOX.bin", new UpgradeCallBack() {
            @Override
            public void onTrans(String direction, String packetData, int progress) {
                LogUtil.e(direction + " " + packetData + " progress:" + progress);
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
}
