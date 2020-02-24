package com.zza.library.utils;

import java.nio.charset.Charset;

/**
 * @Author: 张志安
 * @Mail: zhangzhian_123@qq.com zhangzhian2016@gmail.com
 * @Description: TODO 还需要完善
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
    public static String bytesToHexString(byte[] bytes, int length) {
        String hexStr = "0123456789ABCDEF";
        String result = "";
        String hex = "";

        for (int i = 0; i < length; i++) {
            hex = String.valueOf(hexStr.charAt((bytes[i] & 0xF0) >> 4));
            hex += String.valueOf(hexStr.charAt(bytes[i] & 0x0F));
            result += hex + "";
        }
        return result;
    }

    public static String bytesToHexString(byte[] bytes) {
        return bytesToHexString(bytes, bytes.length);
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

    public static byte[] hexStringToBytes(byte[] hex) {
        return hexStringToBytes(hex, hex.length);
    }


    public static byte[] hexStringToBytes(String hex) {
        byte[] bytes = hex.getBytes();
        return hexStringToBytes(bytes, bytes.length);
    }

    public static String bytesToString(byte bytes[], int offset, int length, Charset charset) {
        return new String(bytes, offset, length, charset);
    }

    public static String bytesToString(byte bytes[], int length, Charset charset) {
        return new String(bytes, 0, length, charset);
    }

    public static String bytesToString(byte bytes[], int length) {
        return new String(bytes, 0, length);
    }


    /**
     * int转byte[]
     * 该方法将一个int类型的数据转换为byte[]形式，因为int为32bit，而byte为8bit所以在进行类型转换时，知会获取低8位，
     * 丢弃高24位。通过位移的方式，将32bit的数据转换成4个8bit的数据。注意 &0xff，在这当中，&0xff简单理解为一把剪刀，
     * 将想要获取的8位数据截取出来。
     *
     * @param i 一个int数字
     * @return byte[]
     */
    public static byte[] intToBytes(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    /**
     * byte[]转int
     * 利用int2ByteArray方法，将一个int转为byte[]，但在解析时，需要将数据还原。同样使用移位的方式，将适当的位数进行还原，
     * 0xFF为16进制的数据，所以在其后每加上一位，就相当于二进制加上4位。同时，使用|=号拼接数据，将其还原成最终的int数据
     *
     * @param bytes byte类型数组
     * @return int数字
     */
    public static int bytesToInt(byte[] bytes) {
        int num = 0;
        if (bytes.length == 4) {
            num = bytes[3] & 0xFF;
            num |= ((bytes[2] << 8) & 0xFF00);
            num |= ((bytes[1] << 16) & 0xFF0000);
            num |= ((bytes[0] << 24) & 0xFF0000);
        }
        if (bytes.length == 2) {
            num = bytes[1] & 0xFF;
            num |= ((bytes[0] << 8) & 0xFF00);
        }
        return num;
    }
}
