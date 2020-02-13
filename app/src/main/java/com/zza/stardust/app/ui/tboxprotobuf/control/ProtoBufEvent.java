package com.zza.stardust.app.ui.tboxprotobuf.control;

import com.zza.stardust.app.ui.tboxprotobuf.IVITboxProto;

/**
 * @Author: 张志安
 * @Mail: zhangzhian_123@qq.com zhangzhian2016@gmail.com
 * @Description:
 * @CreateDate: 2020/2/12 17:02
 * @UpdateDate: 2020/2/12 17:02
 * @UpdateRemark:
 * @Version: 1.0
 */
public class ProtoBufEvent {
    private int msgId;
    private String msg;
    private int curTimes;
    private Boolean period;
    private int periodTimeMils;
    private IVITboxProto.TopMessage topMessage;

    public ProtoBufEvent() {

    }

    public ProtoBufEvent(int msgId, String msg, IVITboxProto.TopMessage topMessage, Boolean period, int periodTimeMils) {
        this.msgId = msgId;
        this.msg = msg;
        this.curTimes = 0;
        this.period = period;
        this.topMessage = topMessage;
        this.periodTimeMils = periodTimeMils;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCurTimes() {
        return curTimes;
    }

    public void setCurTimes(int curTimes) {
        this.curTimes = curTimes;
    }

    public Boolean getPeriod() {
        return period;
    }

    public void setPeriod(Boolean period) {
        this.period = period;
    }

    public int getPeriodTimeMils() {
        return periodTimeMils;
    }

    public void setPeriodTimeMils(int periodTimeMils) {
        this.periodTimeMils = periodTimeMils;
    }

    public IVITboxProto.TopMessage getTopMessage() {
        return topMessage;
    }

    public void setTopMessage(IVITboxProto.TopMessage topMessage) {
        this.topMessage = topMessage;
    }
}
