package com.zza.stardust.app.ui.tboxupgrade;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.Guideline;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zza.library.base.BasePresenter;
import com.zza.library.utils.FileChooseUtil;
import com.zza.library.utils.LogUtil;
import com.zza.library.utils.ToastUtil;
import com.zza.stardust.R;
import com.zza.stardust.app.dialog.CarmakerChooseDialog;
import com.zza.stardust.app.ui.tboxupgrade.api.YodoTBoxSDK;
import com.zza.stardust.base.MActivity;
import com.zza.stardust.bean.CarmakerBean;
import com.zza.stardust.callback.UpgradeCallBack;
import com.zza.stardust.common.MAppTypeInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

public class UpgradeWifiActivity extends MActivity {

    protected static final int REQUEST_CHOOSEFILE = 1;
    private static final int CODE_FOR_WRITE_PERMISSION = 1;

    @BindView(R.id.bt_start)
    Button btStart;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.et_filename)
    EditText etFilename;
    @BindView(R.id.tv_filepath)
    TextView tvFilepath;
    @BindView(R.id.bt_download)
    Button btDownload;
    @BindView(R.id.bt_choose_path)
    Button btChoosePath;
    @BindView(R.id.et_sv)
    EditText etSv;
    @BindView(R.id.et_hv)
    EditText etHv;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_choose_car_type)
    TextView tvChooseCarType;
    Unbinder unbinder;
    @BindView(R.id.tv_step_title)
    TextView tvStepTitle;
    @BindView(R.id.tv_step_info)
    TextView tvStepInfo;
    @BindView(R.id.guideline)
    Guideline guideline;
    @BindView(R.id.textView9)
    TextView textView9;
    @BindView(R.id.textView10)
    TextView textView10;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.textView7)
    TextView textView7;
    @BindView(R.id.textView8)
    TextView textView8;

    private CarmakerBean carmakerBeanChoose = null;
    private ProgressDialog progressDialog = null;
    private CarmakerChooseDialog carmakerChooseDialog = null;
    private boolean isPermission = false;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_upgrade_wifi;
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

        adaptationVersion();
    }

    private void adaptationVersion() {
        if (MAppTypeInfo.APP_TYPE == MAppTypeInfo.APP_YD_ZT) {

            tvStepInfo.setText(getText(R.string.upgrade_step_info_zt));
            textView5.setVisibility(View.GONE);
            textView7.setVisibility(View.GONE);
            tvChooseCarType.setVisibility(View.GONE);
            etFilename.setVisibility(View.GONE);
            btDownload.setVisibility(View.GONE);
            tvChooseCarType.setVisibility(View.GONE);
            carmakerBeanChoose = new CarmakerBean();
            carmakerBeanChoose.setCarmakeId("1");
            carmakerBeanChoose.setCarmakeName("众泰");
            carmakerBeanChoose.setCarmakeIp("192.168.100.1");
        } else {
            initCarmakerDialog();
        }
    }

    private void initCarmakerDialog() {
        List<CarmakerBean> lists = new ArrayList<>();
        CarmakerBean carmakerBean1 = new CarmakerBean();
        carmakerBean1.setCarmakeId("1");
        carmakerBean1.setCarmakeName("众泰");
        carmakerBean1.setCarmakeIp("192.168.100.1");
        lists.add(carmakerBean1);
        CarmakerBean carmakerBean2 = new CarmakerBean();
        carmakerBean2.setCarmakeId("2");
        carmakerBean2.setCarmakeName("云度");
        carmakerBean2.setCarmakeIp("192.168.225.1");
        lists.add(carmakerBean2);
        CarmakerBean carmakerBean3 = new CarmakerBean();
        carmakerBean3.setCarmakeId("3");
        carmakerBean3.setCarmakeName("通用");
        carmakerBean3.setCarmakeIp("192.168.225.1");
        lists.add(carmakerBean3);
        carmakerBeanChoose = carmakerBean3;
        carmakerChooseDialog = new CarmakerChooseDialog(this, lists) {
            @Override
            public void onChooseReviewer(View view, int position) {
                chooseCarmakerData(lists.get(position));
                dismiss();
            }
        };
    }

    public void chooseCarmakerData(CarmakerBean bean) {
        carmakerBeanChoose = bean;
        tvChooseCarType.setText(carmakerBeanChoose.getCarmakeName());
    }

    @OnClick({R.id.bt_start, R.id.bt_download, R.id.bt_choose_path, R.id.tv_choose_car_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_start:
                if (TextUtils.isEmpty(tvFilepath.getText().toString())) {
                    ToastUtil.show("请选择路径");
                } else if (tvChooseCarType.getVisibility() == View.VISIBLE && TextUtils.isEmpty(tvChooseCarType.getText().toString())) {
                    ToastUtil.show("请选择车企");
                } else if (TextUtils.isEmpty(etHv.getText().toString())) {
                    ToastUtil.show("请输入软件版本号");
                } else if (TextUtils.isEmpty(etSv.getText().toString())) {
                    ToastUtil.show("请选择硬件版本号");
                } else if (etHv.getText().toString().length() >= 31) {
                    ToastUtil.show("请输入正确软件版本号");
                } else if (etSv.getText().toString().length() >= 31) {
                    ToastUtil.show("请输入正确硬件版本号");
                } else if (!isPermission) {
                    ToastUtil.show("无读写权限，请授予权限后重试");
                } else {
                    startUpgrade();
                }
                break;
            case R.id.bt_download:
                break;
            case R.id.bt_choose_path:
                chooseLocalFilePath();
                break;
            case R.id.tv_choose_car_type:
                chooseCarmaker(view);
                break;

        }
    }


    private void startUpgrade() {
        if (progressDialog == null)
            progressDialog = ProgressDialog.show(this, "提示", "正在传输升级文件", false, false);
        tvResult.setText("");
        YodoTBoxSDK sdk = YodoTBoxSDK.getInstance();
        sdk.UpgradeTBox(Environment.getExternalStorageDirectory().getPath() + "/TBoxData/TBOX.bin", new UpgradeCallBack() {
            @Override
            public void onTrans(String direction, String packetData, int progress) {
                LogUtil.i(direction + " " + packetData + " progress:" + progress);
            }

            @Override
            public void onTransSuccess() {
                LogUtil.i("onTransSuccess");
            }

            @Override
            public void onTransFail(int i, Exception e) {
                LogUtil.i("onTransFinsh:" + i + " " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvResult.setText("onTransFinsh:" + i + " " + e.getMessage());
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                            progressDialog = null;
                        }
                    }
                });
            }

            @Override
            public void onUpgradeStart() {
                LogUtil.i("onUpgradeStart");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.setMessage("正在升级TBox");
                    }
                });
            }

            @Override
            public void onUpgradeSuccess() {
                LogUtil.i("onUpgradeSuccess");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvResult.setText("onUpgradeSuccess");
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                            progressDialog = null;
                        }
                    }
                });
            }

            @Override
            public void onUpgradeFail(byte b, Exception e) {
                LogUtil.i("onUpgradeFail:" + b + " " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvResult.setText("onUpgradeFail:" + b + " " + e.getMessage());
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                            progressDialog = null;
                        }
                    }
                });
            }
        });
    }

    private void chooseCarmaker(View view) {
        carmakerChooseDialog.showDialog();
    }

    private void chooseLocalFilePath() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        //调用系统文件管理器打开指定路径目录
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。

        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CHOOSEFILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CHOOSEFILE:
                    String path;
                    Uri uri = data.getData();
                    path = FileChooseUtil.getInstance(this).getChooseFileResultPath(uri);
                    setTvFilepath(path);
                    LogUtil.i("File path:" + path);
                    break;
            }
        }
    }

    protected void setTvFilepath(String path) {
        tvFilepath.setText(path);
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
