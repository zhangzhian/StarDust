package com.zza.library.utils;

/**
 * @Author: 张志安
 * @Mail: zhangzhian_123@qq.com zhangzhian2016@gmail.com
 * @Description:
 * @CreateDate: 2020/2/12 15:16
 * @UpdateDate: 2020/2/12 15:16
 * @UpdateRemark:
 * @Version: 1.0
 */
public class BytesUtils {

    /**
     * 将字节数组转换成十六进制的字符串
     *
     * @return
     */
    public static String bytesToHexString(byte[] bytes) {
        String hexStr = "0123456789ABCDEF";
        String result = "";
        String hex = "";
        for (byte b : bytes) {
            hex = String.valueOf(hexStr.charAt((b & 0xF0) >> 4));
            hex += String.valueOf(hexStr.charAt(b & 0x0F));
            result += hex + "";
        }
        return result;
    }

    public static byte[] hexStringToBytes(byte[] hex, int size) {

        byte[] bytes = new byte[size / 2];

        for (int i = 0; i < size; i += 2) {

            char charTemp1 = (char) hex[i];
            char charTemp2 = (char) hex[i + 1];
            StringBuffer temp = new StringBuffer();
            temp.append(charTemp1);
            temp.append(charTemp2);
            bytes[i / 2] = (byte) Integer.parseInt(temp.toString(), 16);

        }

        return bytes;
    }

    public static String bytesToString(byte bytes[], int offset, int length) {
        return new String(bytes, offset, length);
    }

    public static String bytesToString(byte bytes[], int length) {
        return new String(bytes, 0, length);
    }
}
