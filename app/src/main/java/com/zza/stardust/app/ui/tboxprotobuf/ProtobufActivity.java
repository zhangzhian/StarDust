package com.zza.stardust.app.ui.tboxprotobuf;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zza.library.base.BasePresenter;
import com.zza.library.common.lmpl.IOnItemClickListener;
import com.zza.library.utils.TimeUtil;
import com.zza.library.utils.ToastUtil;
import com.zza.library.weight.ListDialog;
import com.zza.stardust.R;
import com.zza.stardust.app.ui.tboxprotobuf.control.ProtobufControl;
import com.zza.stardust.app.ui.tboxprotobuf.control.ProtobufControlImpl;
import com.zza.stardust.app.ui.tboxprotobuf.control.ProtobufListener;
import com.zza.stardust.app.ui.tboxprotobuf.control.ProtobufMessageMange;
import com.zza.stardust.base.MActivity;
import com.zza.stardust.common.MNetInfo;

import butterknife.BindView;
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
public class ProtobufActivity extends MActivity implements ProtobufListener, IOnItemClickListener {

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

    private ProtobufControl control = null;
    private StringBuffer bufferShowConn = new StringBuffer();
    private StringBuffer bufferShowRece = new StringBuffer();

    private int msgId = 0;
    private boolean connecting = false;
    private ListDialog dialog;
    private boolean isPause = false;

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
        control = ProtobufControlImpl.getInstance();
        clearShowData();
    }

    @OnClick({R.id.tv_choose_data, R.id.tv_send_data, R.id.tv_muit_send, R.id.tv_clear_text,
            R.id.tv_conn, R.id.tv_disconn, R.id.tv_send_heart, R.id.tv_stop_heart, R.id.tv_rece_msg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_choose_data:
                showListDialog();
                break;
            case R.id.tv_send_data:
                if (connecting) {
                    control.sendCmd(msgId++, etSendMsg.getText().toString(), false, 0);
                } else {
                    ToastUtil.show("未连接");
                }
                break;
            case R.id.tv_muit_send:
                if (connecting) {
                    control.sendCmd(msgId++, etSendMsg.getText().toString(), true, 10 * 1000);
                } else {
                    ToastUtil.show("未连接");
                }
                break;
            case R.id.tv_clear_text:
                clearShowData();
                break;
            case R.id.tv_conn:
                if (!connecting) {
                    connetServer();
                } else {
                    ToastUtil.show("已连接");
                }
                break;
            case R.id.tv_disconn:
                if (connecting) {
                    control.disConnect();
                } else {
                    ToastUtil.show("未连接");
                }
                break;
            case R.id.tv_send_heart:
                if (connecting) {
                    try {
                        String jsonStr = ProtobufMessageMange.parseMessage2JSONString(ProtobufMessageMange.getMessageByMessagetype(IVITboxProto.Messagetype.REQUEST_HEARTBEAT_SIGNAL));
                        control.sendCmd(0, jsonStr, true, 20 * 1000);
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtil.show("未连接");
                }
                break;
            case R.id.tv_stop_heart:
                if (connecting) {
                    control.stopCmd(0);
                    ToastUtil.show("已停止");
                } else {
                    ToastUtil.show("未连接");
                }
                break;
            case R.id.tv_rece_msg:
                isPause = !isPause;
                break;
        }
    }

    private void showListDialog() {
        dialog = new ListDialog(this, this, ProtobufMessageMange.getAllMessage(), "选择命令");
        dialog.show();
    }

    private void clearShowData() {
        bufferShowRece.setLength(0);
        bufferShowRece.append("接收到的数据：" + "\r\n");
        tvReceMsg.setText(bufferShowRece.toString());
        bufferShowConn.setLength(0);
        bufferShowConn.append("连接数据：" + "\r\n");
        tvLog.setText(bufferShowConn.toString());
    }

    private void connetServer() {
        control.init(MNetInfo.YD_TBOX_IP, MNetInfo.YD_PROTOBUF_PORT, this);
        control.connect();
        tvLog.setText(bufferShowConn.append("正在连接" + "\r\n").toString());
    }

    @Override
    public void receData(IVITboxProto.TopMessage data) {
        showMessage(tvReceMsg, bufferShowRece, "["
                + TimeUtil.stampToHMS(System.currentTimeMillis()) + "]\r\n"
                + ProtobufMessageMange.paraseMessage2Bytes(data) + "\r\n"
                + data.toString() + "\r\n");
    }

    @Override
    public void onConnectSuccess() {
        connecting = true;
        showMessage(tvLog, bufferShowConn, "["
                + TimeUtil.stampToHMS(System.currentTimeMillis()) + "] 连接成功\r\n");
    }

    @Override
    public void onConnectFail(int code, Exception e) {
        showMessage(tvLog, bufferShowConn, "["
                + TimeUtil.stampToHMS(System.currentTimeMillis()) + "] 连接失败 " + code + "\r\n");
    }

    @Override
    public void onDisConnect() {
        connecting = false;
        showMessage(tvLog, bufferShowConn, "["
                + TimeUtil.stampToHMS(System.currentTimeMillis()) + "] 连接断开\r\n");
    }

    @Override
    public void onDisConnectFail(int code, Exception e) {
        showMessage(tvLog, bufferShowConn, "["
                + TimeUtil.stampToHMS(System.currentTimeMillis()) + "] 连接断开失败" + code + "\r\n");
    }

    @Override
    public void sendDataSuccess(int msgId, boolean period) {
        //if (!period)
        showToastMessage("发送成功: " + msgId);
    }

    @Override
    public void sendDataFail(int msgId, boolean period, int code, Exception e) {
        //if (!period)
        showToastMessage("发送失败: " + msgId + "Exception: " + code);
    }

    private void showMessage(TextView tv, StringBuffer buffer, String data) {
        runOnUiThread(() -> {
            if (isPause) {
                if (buffer.length() > 10 * 1024) {
                    return;
                }
            } else {
                if (buffer.length() > 10 * 1024) {
                    buffer.delete(0, 9 * 1024);
                }
                tv.setText(buffer.append(data).toString());

                svLog.post(() -> svLog.fullScroll(ScrollView.FOCUS_DOWN));
                svReceMsg.post(() -> svReceMsg.fullScroll(ScrollView.FOCUS_DOWN));
            }
        });
    }

    private void showToastMessage(String data) {
        runOnUiThread(() -> ToastUtil.show(data));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (connecting) {
            control.removeReceDataListener();
            control.disConnect();
            connecting = false;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(View view, int position) {
        String mesgType = ProtobufMessageMange.getAllMessage().get(position).split("\r\n")[0];
        try {
            etSendMsg.setText(ProtobufMessageMange.getJSONMessageByMessagetype(IVITboxProto.Messagetype.valueOf(mesgType)));
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        dialog.dismiss();

    }
}
