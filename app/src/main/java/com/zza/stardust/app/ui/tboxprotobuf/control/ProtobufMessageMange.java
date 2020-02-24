package com.zza.stardust.app.ui.tboxprotobuf.control;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.zza.stardust.app.ui.tboxprotobuf.IVITboxProto.GPS_SEND_OnOff;
import com.zza.stardust.app.ui.tboxprotobuf.IVITboxProto.Messagetype;
import com.zza.stardust.app.ui.tboxprotobuf.IVITboxProto.MsgResult;
import com.zza.stardust.app.ui.tboxprotobuf.IVITboxProto.NETWORK_SEND_OnOff;
import com.zza.stardust.app.ui.tboxprotobuf.IVITboxProto.TboxChargeAppoointmentSet;
import com.zza.stardust.app.ui.tboxprotobuf.IVITboxProto.TboxChargeCtrl;
import com.zza.stardust.app.ui.tboxprotobuf.IVITboxProto.TboxGPSCmd;
import com.zza.stardust.app.ui.tboxprotobuf.IVITboxProto.TboxNetworkCtrl;
import com.zza.stardust.app.ui.tboxprotobuf.IVITboxProto.TopMessage;

import java.util.ArrayList;
import java.util.List;

import static com.zza.stardust.app.ui.tboxprotobuf.IVITboxProto.Messagetype.IHU_ACTIVESTATE_RESULT;
import static com.zza.stardust.app.ui.tboxprotobuf.IVITboxProto.Messagetype.REQUEST_HEARTBEAT_SIGNAL;
import static com.zza.stardust.app.ui.tboxprotobuf.IVITboxProto.Messagetype.REQUEST_NETWORK_SIGNAL_STRENGTH;
import static com.zza.stardust.app.ui.tboxprotobuf.IVITboxProto.Messagetype.REQUEST_TBOX_CHARGEAPPOINTMENTSET;
import static com.zza.stardust.app.ui.tboxprotobuf.IVITboxProto.Messagetype.REQUEST_TBOX_CHARGECTRL;
import static com.zza.stardust.app.ui.tboxprotobuf.IVITboxProto.Messagetype.REQUEST_TBOX_GPS_SET;
import static com.zza.stardust.app.ui.tboxprotobuf.IVITboxProto.Messagetype.REQUEST_TBOX_INFO;
import static com.zza.stardust.app.ui.tboxprotobuf.IVITboxProto.Messagetype.RESPONSE_IHU_CHARGEAPPOINTMENTSTS_RESULT;
import static com.zza.stardust.app.ui.tboxprotobuf.IVITboxProto.Messagetype.RESPONSE_IHU_LOGFILE_RESULT;
import static com.zza.stardust.app.ui.tboxprotobuf.IVITboxProto.Messagetype.RESPONSE_TBOX_REMOTEDIAGNOSE_RESULT;

/**
 * @Author: 张志安
 * @Mail: zhangzhian_123@qq.com zhangzhian2016@gmail.com
 * @Description:
 * @CreateDate: 2020/2/10 15:12
 * @UpdateDate: 2020/2/10 15:12
 * @UpdateRemark:
 * @Version: 1.0
 */
public class ProtobufMessageMange {
    public static TopMessage getMessageByMessagetype(Messagetype messagetype) {
        TopMessage.Builder message = TopMessage.newBuilder();
        switch (messagetype) {
            case REQUEST_HEARTBEAT_SIGNAL://心跳
                message.setMessageType(REQUEST_HEARTBEAT_SIGNAL);
                break;
            case REQUEST_NETWORK_SIGNAL_STRENGTH://IHU发送给TBOX用于得到信号强度和制式
                message.setMessageType(Messagetype.REQUEST_NETWORK_SIGNAL_STRENGTH);
                message.setTboxNetworkCtrl(TboxNetworkCtrl.newBuilder()
                        .setOnoff(NETWORK_SEND_OnOff.NETWORK_ON)
                        .setTimeCycle(10)
                        .build());
                break;
            case REQUEST_TBOX_INFO://获取TBOX的信息
                message.setMessageType(Messagetype.REQUEST_TBOX_INFO);
                break;
            case REQUEST_TBOX_GPS_SET://设置GPS信息
                message.setMessageType(Messagetype.REQUEST_TBOX_GPS_SET);
                message.setTboxGpsCtrl(TboxGPSCmd.newBuilder()
                        .setOnoff(GPS_SEND_OnOff.GPS_ON)
                        .setTimeCycle(10)
                        .build());
                break;
            case IHU_ACTIVESTATE_RESULT://绑车激活
                message.setMessageType(Messagetype.IHU_ACTIVESTATE_RESULT);
                message.setMsgResult(MsgResult.newBuilder()
                        .setResult(true)
                        .setErrorCode(ByteString.EMPTY)
                        .build());
                break;
            case RESPONSE_TBOX_REMOTEDIAGNOSE_RESULT://采集视频和图片，IHU回复
                message.setMessageType(Messagetype.RESPONSE_TBOX_REMOTEDIAGNOSE_RESULT);
                message.setMsgResult(MsgResult.newBuilder()
                        .setResult(true)
                        .setErrorCode(ByteString.EMPTY)
                        .build());
                break;
            case RESPONSE_IHU_LOGFILE_RESULT://TBOX通知IHU上传日志文件，IHU回复
                message.setMessageType(Messagetype.RESPONSE_IHU_LOGFILE_RESULT);
                message.setMsgResult(MsgResult.newBuilder()
                        .setResult(true)
                        .setErrorCode(ByteString.EMPTY)
                        .build());
                break;
            case RESPONSE_IHU_CHARGEAPPOINTMENTSTS_RESULT://TBOX 通知 IHU 更新预约充电状态，IHU回复
                message.setMessageType(Messagetype.RESPONSE_IHU_CHARGEAPPOINTMENTSTS_RESULT);
                message.setMsgResult(MsgResult.newBuilder()
                        .setResult(true)
                        .setErrorCode(ByteString.EMPTY)
                        .build());
                break;
            case REQUEST_TBOX_CHARGEAPPOINTMENTSET://IHU 设置预约充电
                message.setMessageType(Messagetype.REQUEST_TBOX_CHARGEAPPOINTMENTSET);
                message.setTboxChargeAppoointmentSet(TboxChargeAppoointmentSet.newBuilder()
                        .setTimestamp((int) (System.currentTimeMillis() / 1000))
                        .setHour(0)
                        .setMin(0)
                        .setId(0)
                        .setTargetpower(0)
                        .setEffectivestate(true)
                        .build());
                break;
            case REQUEST_TBOX_CHARGECTRL://IHU 开启关闭即时充电
                message.setMessageType(Messagetype.REQUEST_TBOX_CHARGECTRL);
                message.setTboxChargectrl(TboxChargeCtrl.newBuilder()
                        .setTimestamp((int) (System.currentTimeMillis() / 1000))
                        .setCommend(true)
                        .setTargetpower(100)
                        .build());
                break;
            case REQUEST_RESPONSE_NONE:// 回复的massage，不可以发送
            case RESPONSE_HEARTBEAT_RESULT:
            case RESPONSE_NETWORK_SIGNAL_STRENGTH:
            case RESPONSE_TBOX_INFO:
            case RESPONSE_TBOX_GPS_SET_RESULT:
            case RESPONSE_TBOX_GPSINFO_RESULT://GPS信息主动上报
            case RESPONSE_TBOX_ACTIVESTATE_RESULT:
            case REQUEST_TBOX_REMOTEDIAGNOSE:
            case REQUEST_IHU_LOGFILE:
            case REQUEST_IHU_CHARGEAPPOINTMENTSTS:
            case RESPONSE_TBOX_CHARGEAPPOINTMENTSET_RESULT:
            case RESPONSE_TBOX_CHARGECTRL_RESULT:
                return null;

        }
        return message.build();
    }

    public static List<String> getAllMessage() {
        List<String> list = new ArrayList<>();
        list.add(REQUEST_HEARTBEAT_SIGNAL + "\r\n心跳");
        list.add(REQUEST_NETWORK_SIGNAL_STRENGTH + "\r\n信号强度和制式");
        list.add(REQUEST_TBOX_INFO + "\r\nTBOX的信息");
        list.add(REQUEST_TBOX_GPS_SET + "\r\n设置GPS信息");
        list.add(IHU_ACTIVESTATE_RESULT + "\r\n绑车激活");
        list.add(RESPONSE_TBOX_REMOTEDIAGNOSE_RESULT + "\r\n回复采集视频和图片");
        list.add(RESPONSE_IHU_LOGFILE_RESULT + "\r\n回复上传日志文件");
        list.add(RESPONSE_IHU_CHARGEAPPOINTMENTSTS_RESULT + "\r\n回复更新预约充电状态");
        list.add(REQUEST_TBOX_CHARGEAPPOINTMENTSET + "\r\nIHU 设置预约充电");
        list.add(REQUEST_TBOX_CHARGECTRL + "\r\nIHU 开启关闭即时充电");
        return list;
    }


    public static String getJSONMessageByMessagetype(Messagetype messagetype) throws InvalidProtocolBufferException {
        return parseMessage2JSONString(getMessageByMessagetype(messagetype));
    }

    public static TopMessage paraseBytes2Message(byte[] data) throws InvalidProtocolBufferException {
        return TopMessage.parseFrom(data);
    }

    public static byte[] paraseMessage2Bytes(TopMessage msg) {
        return msg.toByteArray();
    }

    public static String parseMessage2JSONString(TopMessage message) throws InvalidProtocolBufferException {
        return JsonFormat.printer().print(message);
    }

    public static TopMessage paraseJSONString2Message(String message, TopMessage.Builder builder)
            throws InvalidProtocolBufferException {
        JsonFormat.parser().merge(message, builder);
        return builder.build();
    }

}
