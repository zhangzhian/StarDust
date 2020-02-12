package com.zza.stardust.app.ui.tboxprotobuf.control;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.zza.stardust.app.ui.tboxprotobuf.IVITboxProto.*;

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
                message.setMessageType(Messagetype.REQUEST_HEARTBEAT_SIGNAL);
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
            case REQUEST_TBOX_REMOTEDIAGNOSE://采集视频和图片
                message.setMessageType(Messagetype.REQUEST_TBOX_REMOTEDIAGNOSE);
                message.setTboxRemotedaignose(TboxRemoteDiagnose.newBuilder()
                        .setVin("")
                        .setEventid(0)
                        .setTimestamp((int) (System.currentTimeMillis()/1000))
                        .setDatatype(DataTypeEnum.PHOTO_TYPE)
                        .setCameraname(CameraNameEnum.DVR_TYPE)
                        .setAid(0)
                        .setMid(0)
                        .setEffectivetime(0)
                        .setSizelimit(0)
                        .build());
                break;
            case REQUEST_IHU_LOGFILE://TBOX通知IHU上传日志文件
                message.setMessageType(Messagetype.REQUEST_IHU_LOGFILE);
                message.setIhuLogfile(IhuLogfile.newBuilder()
                        .setVin("")
                        .setEventid(0)
                        .setTimestamp((int) (System.currentTimeMillis()/1000))
                        .setAid(0)
                        .setMid(0)
                        .setStarttime((int) (System.currentTimeMillis()/1000))
                        .setDurationtime(0)
                        .setChannel(1)
                        .setLevel(1)
                        .build());
                break;
            case REQUEST_IHU_CHARGEAPPOINTMENTSTS://TBOX 通知 IHU 更新预约充电状态
                message.setMessageType(Messagetype.REQUEST_IHU_CHARGEAPPOINTMENTSTS);
                message.setIhuChargeAppoointmentSts(IhuChargeAppoointmentSts.newBuilder()
                        .setTimestamp((int) (System.currentTimeMillis()/1000))
                        .setHour(0)
                        .setMin(0)
                        .setId(0)
                        .setTargetpower(0)
                        .setEffectivestate(true)
                        .build());
                break;
            case REQUEST_TBOX_CHARGEAPPOINTMENTSET://IHU 设置预约充电
                message.setMessageType(Messagetype.REQUEST_TBOX_CHARGEAPPOINTMENTSET);
                message.setTboxChargeAppoointmentSet(TboxChargeAppoointmentSet.newBuilder()
                        .setTimestamp((int) (System.currentTimeMillis()/1000))
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
                        .setTimestamp((int) (System.currentTimeMillis()/1000))
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
            case RESPONSE_TBOX_REMOTEDIAGNOSE_RESULT:
            case RESPONSE_IHU_LOGFILE_RESULT:
            case RESPONSE_IHU_CHARGEAPPOINTMENTSTS_RESULT:
            case RESPONSE_TBOX_CHARGEAPPOINTMENTSET_RESULT:
            case RESPONSE_TBOX_CHARGECTRL_RESULT:
                return null;

        }
        return message.build();
    }

    public static TopMessage paraseBytes2Message(byte[] data) throws InvalidProtocolBufferException {
        return TopMessage.parseFrom(data);
    }


    public static String message2JSONString(TopMessage message) throws InvalidProtocolBufferException {
        return JsonFormat.printer().print(message);
    }

    public static TopMessage paraseJSONString2Message(String message, TopMessage.Builder builder)
            throws InvalidProtocolBufferException {
        JsonFormat.parser().merge(message, builder);
        return builder.build();
    }

}
