package com.zza.stardust.app.ui.tboxprotobuf;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zza.library.base.BasePresenter;
import com.zza.stardust.R;
import com.zza.stardust.base.MActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author: 张志安
 * @Mail: zhangzhian_123@qq.com zhangzhian2016@gmail.com
 * @Description: java api: https://developers.google.cn/protocol-buffers/
 * github: https://github.com/protocolbuffers/protobuf
 * 资料: https://developers.google.cn/protocol-buffers/
 * @CreateDate: 2020/2/5 17:47
 * @UpdateDate: 2020/2/5 17:47
 * @UpdateRemark:
 * @Version: 1.0
 */
public class ProtobufActivity extends MActivity {

    @BindView(R.id.tv_log)
    TextView tvLog;
    @BindView(R.id.sv_log)
    ScrollView svLog;
    @BindView(R.id.tv_rece_msg)
    TextView tvReceMsg;
    @BindView(R.id.sv_rece_msg)
    ScrollView svReceMsg;
    @BindView(R.id.et_send_msg)
    EditText etSendMsg;
    @BindView(R.id.sv_send_msg)
    ScrollView svSendMsg;
    @BindView(R.id.tv_choose_data)
    TextView tvChooseData;
    @BindView(R.id.tv_send_data)
    TextView tvSendData;
    @BindView(R.id.tv_muit_send)
    TextView tvMuitSend;
    @BindView(R.id.tv_clear_text)
    TextView tvClearText;
    @BindView(R.id.tv_conn)
    TextView tvConn;
    @BindView(R.id.tv_disconn)
    TextView tvDisconn;
    @BindView(R.id.tv_send_heart)
    TextView tvSendHeart;
    @BindView(R.id.tv_stop_heart)
    TextView tvStopHeart;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_protobuf;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        super.onInit(savedInstanceState);
    }

    @OnClick({R.id.tv_choose_data, R.id.tv_send_data, R.id.tv_muit_send, R.id.tv_clear_text, R.id.tv_conn, R.id.tv_disconn, R.id.tv_send_heart, R.id.tv_stop_heart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_choose_data:
                break;
            case R.id.tv_send_data:
                break;
            case R.id.tv_muit_send:
                break;
            case R.id.tv_clear_text:
                break;
            case R.id.tv_conn:
                break;
            case R.id.tv_disconn:
                break;
            case R.id.tv_send_heart:
                break;
            case R.id.tv_stop_heart:
                break;
        }
    }
}
