package com.zza.stardust.app.ui.tboxupgrade;

public class UpgradeUtils {
    public static byte[] protocolMake(String svStr, String hvStr, String fileNameStr) {
        byte[] result = new byte[10 + 20 + 32 * 3 + 1];

        //Header Message Header 报文头，固定4个字节，此处恒为：（0x59,0x44,0x53,0x2E）
        result[0] = 0x59;
        result[1] = 0x44;
        result[2] = 0x53;
        result[3] = 0x2E;
        //Test Flag 测试标志，长度为1个字节。0x00：正常业务；0x01：测试业务。
        result[4] = 0x00;
        //Message Size 编码后的消息长度(Dispatcher Message + Application Data)，长度为2个字节。
        result[5] = 0x00;
        result[6] = 0x74;
        //Dispatcher Message Length 编码后Dispatcher Message 的长度，长度为2个字节。
        result[7] = 0x00;
        result[8] = 0x14;
        // Dispatcher MessageSecurity Context Version Dispatcher Message 密钥版本号，长度为1个字节。为0时为不加密。
        result[9] = 0x00;

        String currentTime = Long.toString(System.currentTimeMillis() / 1000, 16).toUpperCase();
        //Event Creation Time eventCreationTime 4 UINT32 0.. 4294967295 √ √
        //System.arraycopy(currentTime, 0, result, 10, currentTime.length);
        result[10] = (byte) Integer.parseInt((currentTime.substring(0, 2)), 16);
        result[11] = (byte) Integer.parseInt((currentTime.substring(2, 4)), 16);
        result[12] = (byte) Integer.parseInt((currentTime.substring(4, 6)), 16);
        result[13] = (byte) Integer.parseInt((currentTime.substring(6, 8)), 16);
        //Event ID eventId 6 BYTE Size(6)
        result[14] = 0x00;
        result[15] = 0x00;
        result[16] = 0x00;
        result[17] = 0x00;
        result[18] = 0x00;
        result[19] = 0x00;
        //Application ID applicationId 2 UINT16 0.. 65535
        result[20] = 0x2C;
        result[21] = 0x01;
        // Message ID messageId 2 UINT16 0.. 65535
        result[22] = 0x00;
        result[23] = 0x01;
        // Message Counter uplinkCounter 1 UINT8 0..255
        result[24] = 0x03;
        // downlinkCounter 1 UINT8 0..255
        result[25] = 0x00;
        // Application Data Length applicationDataLength 2 UINT16 0.. 65535
        result[26] = 0x00;
        result[27] = 0x60;
        //Result result 2 UINT16 0…65535
        result[28] = 0x00;
        result[29] = 0x00;

        String fileName = fileNameStr;
        System.arraycopy(fileName.getBytes(), 0, result, 30, fileName.length());
        String sv = svStr;
        String hv = hvStr;
        System.arraycopy(sv.getBytes(), 0, result, 30 + 32, sv.length());
        System.arraycopy(hv.getBytes(), 0, result, 30 + 32 + 32, hv.length());
        result[126] = getXor(result);
        return result;
    }

    public static byte getXor(byte[] datas) {

        byte temp = datas[0];

        for (int i = 1; i < datas.length - 1; i++) {
            temp ^= datas[i];
        }

        return temp;
    }

    public static String errorCode2Str(byte errorCode){
        String exceptionStr = "";
        switch (errorCode) {
            case 2:
                exceptionStr = "上次升级没结束";
                break;
            case 3:
                exceptionStr = "条件不满足";
                break;
            case 4:
                exceptionStr = "用户取消";
                break;
            case 5:
                exceptionStr = "用户延迟";
                break;
            case 6:
                exceptionStr = "超时";
                break;
            case 7:
                exceptionStr = "下载失败";
                break;
            case 8:
                exceptionStr = "启动信息失败";
                break;
            case 9:
                exceptionStr = "校验失败";
                break;
            case 10:
                exceptionStr = "pwr为off或acc";
                break;
            case 11:
                exceptionStr = "pwr为on";
                break;
        }
        return exceptionStr;
    }
}
