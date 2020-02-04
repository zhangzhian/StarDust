package com.zza.stardust.bean;

public class CarmakerBean {
    private String carmakeId;
    private String carmakeName;
    private String carmakeIp;

    public String getCarmakeId() {
        return carmakeId;
    }

    public void setCarmakeId(String carmakeId) {
        this.carmakeId = carmakeId;
    }

    public String getCarmakeName() {
        return carmakeName;
    }

    public void setCarmakeName(String carmakeName) {
        this.carmakeName = carmakeName;
    }

    public String getCarmakeIp() {
        return carmakeIp;
    }

    public void setCarmakeIp(String carmakeIp) {
        this.carmakeIp = carmakeIp;
    }

    @Override
    public String toString() {
        return "CarmakerBean{" +
                "carmakeId='" + carmakeId + '\'' +
                ", carmakeName='" + carmakeName + '\'' +
                ", carmakeIp='" + carmakeIp + '\'' +
                '}';
    }
}
