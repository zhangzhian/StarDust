package com.zza.stardust.app.ui.tboxprotobuf.control;

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
    public static String getMessageByMessagetype(Messagetype messagetype) {
        String message = "";
        switch (messagetype){
            case REQUEST_HEARTBEAT_SIGNAL://心跳
                break;
            case REQUEST_NETWORK_SIGNAL_STRENGTH://IHU发送给TBOX用于得到信号强度和制式
                break;
            case REQUEST_TBOX_INFO://获取TBOX的信息
                break;
            case REQUEST_TBOX_GPS_SET://设置GPS信息
                break;
            case IHU_ACTIVESTATE_RESULT://绑车激活
                break;
            case REQUEST_TBOX_REMOTEDIAGNOSE://采集视频和图片
                break;
            case REQUEST_IHU_LOGFILE://TBOX通知IHU上传日志文件
                break;
            case REQUEST_IHU_CHARGEAPPOINTMENTSTS://TBOX 通知 IHU 更新预约充电状态
                break;
            case REQUEST_TBOX_CHARGEAPPOINTMENTSET://IHU 设置预约充电
                break;
            case REQUEST_TBOX_CHARGECTRL://IHU 开启关闭即时充电
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
                break;
        }
        return message;
    }
}
