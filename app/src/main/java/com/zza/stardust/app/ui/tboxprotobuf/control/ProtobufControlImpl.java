package com.zza.stardust.app.ui.tboxprotobuf.control;

import android.text.TextUtils;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.zza.library.utils.BytesUtils;
import com.zza.library.utils.LogUtil;
import com.zza.library.utils.StringUtils;
import com.zza.stardust.app.ui.tboxprotobuf.IVITboxProto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author: 张志安
 * @Mail: zhangzhian_123@qq.com zhangzhian2016@gmail.com
 * @Description:
 * @CreateDate: 2020/2/10 15:02
 * @UpdateDate: 2020/2/10 15:02
 * @UpdateRemark:
 * @Version: 1.0
 */
public class ProtobufControlImpl implements ProtobufControl {

    private static final int NO_ERROR = 0;
    private static final int ERR_NOT_INIT = -1;
    private static final int ERR_INTERRUPTED = -2;
    private static final int ERR_UNKNOWN_HOST = -3;
    private static final int ERR_IO = -4;
    private static final int ERR_INVALID_PROTOCOLBUFFER = -5;
    private static final int ERR_ISRUNNING = -6;
    private static final int ERR_CONNECT = -7;
    private static final int ERR_IO_CLOSE = -8;
    private static final int ERROR = -99;

    private static final int SEND_PERIOD = 100;

    private static final String PROTOBUF_START = "#START*";
    private static final String PROTOBUF_END = "#END*";

    private static final String PROTOBUF_START_HEX = StringUtils.strToHexStr(PROTOBUF_START);
    private static final String PROTOBUF_END_HEX = StringUtils.strToHexStr(PROTOBUF_END);

    private static final int PROTOBUF_LENGTH_SIZE = 4;
    private volatile static ProtobufControlImpl uniqueInstance;

    private ProtobufListener listener = null;
    private Thread threadTcp = null;
    private Thread threadSend = null;

    private Boolean isRunning = false;

    private Socket socket = null;
    private OutputStream os = null;//字节输出流
    //private PrintWriter pw = null;
    private InputStream is = null;

    private String host;
    private int port;

    private CopyOnWriteArrayList<ProtoBufEvent> buffList = new CopyOnWriteArrayList<>();
    private int currentPos = 0;

    private ProtobufControlImpl() {
    }

    public static ProtobufControlImpl getInstance() {
        if (uniqueInstance == null) {
            synchronized (ProtobufControlImpl.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new ProtobufControlImpl();
                }
            }
        }
        return uniqueInstance;
    }

    @Override
    public void init(String host, int port, ProtobufListener listener) {
        this.host = host;
        this.port = port;
        this.listener = listener;
        if (threadTcp == null) {
            threadTcp = new Thread(new TcpRunnable());
        }
        if (threadSend == null) threadSend = new Thread(new TcpSendRunning());
    }

    @Override
    public void connect() {
        if (threadTcp != null) {
            if (!isRunning) {
                threadTcp.start();
                if (threadSend != null) threadSend.start();
                isRunning = true;
            } else {
                if (listener != null)
                    listener.onConnectFail(ERR_ISRUNNING, new Exception("thread is running."));
            }
        } else {
            if (listener != null) listener.onConnectFail(ERR_NOT_INIT, new Exception("not init."));
        }
    }

    @Override
    public void disConnect() {
        //4.关闭资源
        try {

            buffList.clear();

            if (is != null) {
                is.close();
                is = null;
            }
            if (os != null) {
                os.close();
                os = null;
            }

            if (socket != null) {
                socket.close();
                socket = null;
            }

            isRunning = false;

            if (threadTcp != null) threadTcp = null;

            if (threadSend != null) threadSend = null;

            if (listener != null) {
                listener.onDisConnect();
                listener = null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            if (listener != null) listener.onDisConnectFail(ERR_IO_CLOSE, e);
            isRunning = false;
        }
    }

    @Override
    public void sendCmd(int msgId, String cmd, boolean period, int periodTimeMils) {
        try {
            IVITboxProto.TopMessage topMessage = ProtobufMessageMange.paraseJSONString2Message(cmd, IVITboxProto.TopMessage.newBuilder());
            buffList.add(new ProtoBufEvent(msgId, cmd, topMessage, period, periodTimeMils < 1000 ? 1000 : periodTimeMils));
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            if (listener != null)
                listener.sendDataFail(msgId, period, ERR_INVALID_PROTOCOLBUFFER, e);
        }
    }

    @Override
    public void stopCmd(int msgId) {
        for (int i = 0; i < buffList.size(); i++) {
            if (buffList.get(i).getMsgId() == msgId)
                buffList.remove(i);
        }
    }

    @Override
    public void addReceDataListener(ProtobufListener listener) {
        this.listener = listener;
    }

    @Override
    public void removeReceDataListener() {
        this.listener = null;
    }


    class TcpRunnable implements Runnable {

        @Override
        public void run() {
            try {
                //接收服务器的响应
                byte[] buf = new byte[10300];
                int readSize = 0;

                //创建客户端Socket，指定服务器地址和端口
                socket = new Socket(host, port);
                //获取输出流，向服务器端发送信息
                os = socket.getOutputStream();
                //pw = new PrintWriter(os);
                //获取输入流，并读取服务器端的响应信息
                is = socket.getInputStream();

                if (listener != null) listener.onConnectSuccess();

                while (isRunning && (readSize = is.read(buf)) != -1) {
                    //将字节数组转换成十六进制的字符串
                    //String dataStr = BytesUtils.bytesToHexString(buf);

                    String dataStr = BytesUtils.bytesToHexString(buf, readSize);
                    LogUtil.i("rece:" + readSize + ":" + dataStr);
                    paraseReceData(dataStr);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                if (isRunning && listener != null) listener.onConnectFail(ERR_INTERRUPTED, e);
            } catch (UnknownHostException e) {
                e.printStackTrace();
                if (isRunning && listener != null) listener.onConnectFail(ERR_UNKNOWN_HOST, e);
            } catch (ConnectException e) {
                e.printStackTrace();
                if (isRunning && listener != null) listener.onConnectFail(ERR_CONNECT, e);
            } catch (IOException e) {
                e.printStackTrace();
                if (isRunning && listener != null) listener.onConnectFail(ERR_IO, e);
            } finally {
                if (isRunning) disConnect();
            }

        }

        private void paraseReceData(String dataStr) throws UnsupportedEncodingException {
            int startIndex;
            int endIndex = 0;
            while (!TextUtils.isEmpty(dataStr)
                    && (startIndex = dataStr.indexOf(PROTOBUF_START_HEX)) >= 0
                    && (endIndex = dataStr.indexOf(PROTOBUF_END_HEX)) > 0
                    && endIndex - startIndex > PROTOBUF_LENGTH_SIZE + PROTOBUF_START_HEX.length()
            ) {

                String size_hex = dataStr.substring(startIndex + PROTOBUF_START_HEX.length(),
                        startIndex + PROTOBUF_START_HEX.length() + PROTOBUF_LENGTH_SIZE);
                byte[] proto_byte = BytesUtils.hexStringToBytes(dataStr.substring(startIndex +
                        PROTOBUF_START_HEX.length() + PROTOBUF_LENGTH_SIZE, endIndex));
                int size_value = Integer.parseInt(size_hex, 16);

                if (size_value == proto_byte.length) {
                    try {
                        IVITboxProto.TopMessage topMessage = ProtobufMessageMange.paraseBytes2Message(proto_byte);
                        listener.receData(topMessage);
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                    }
                }
                dataStr = dataStr.substring(endIndex + PROTOBUF_END_HEX.length());
            }
        }
    }

    class TcpSendRunning implements Runnable {

        @Override
        public void run() {
            while (isRunning) {
                try {
                    if (buffList.size() == 0) {
                        Thread.sleep(1000);
                    } else {
                        currentPos++;
                        if (currentPos >= buffList.size()) {
                            currentPos = 0;
                        }
                        ProtoBufEvent protoBufEvent = buffList.get(currentPos);
                        if (protoBufEvent.getPeriod()) {
                            int times = protoBufEvent.getPeriodTimeMils() / SEND_PERIOD;
                            if (protoBufEvent.getCurTimes() == 0 || protoBufEvent.getCurTimes() >= times) {
                                sendData(protoBufEvent);
                                protoBufEvent.setCurTimes(1);
                            } else {
                                protoBufEvent.setCurTimes(protoBufEvent.getCurTimes() + 1);
                            }
                        } else {
                            sendData(protoBufEvent);
                            buffList.remove(currentPos);
                        }
                        Thread.sleep(SEND_PERIOD);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void sendData(ProtoBufEvent msg) {
            try {
                os.write(getPocketData(msg.getTopMessage().toByteArray()));
                os.flush();
                if (listener != null) listener.sendDataSuccess(msg.getMsgId(), msg.getPeriod());
            } catch (IOException e) {
                e.printStackTrace();
                if (listener != null)
                    listener.sendDataFail(msg.getMsgId(), msg.getPeriod(), ERR_IO, e);
            }
        }

        private byte[] getPocketData(byte[] bytes) {
            byte[] pocketData = new byte[PROTOBUF_START.length() + PROTOBUF_END.length() + 2 + bytes.length];
            System.arraycopy(PROTOBUF_START.getBytes(), 0, pocketData, 0, PROTOBUF_START.length());
            pocketData[PROTOBUF_START.length()] = (byte) (bytes.length / 16);
            pocketData[PROTOBUF_START.length() + 1] = (byte) (bytes.length % 16);
            System.arraycopy(bytes, 0, pocketData, PROTOBUF_START.length() + 2, bytes.length);
            System.arraycopy(PROTOBUF_END.getBytes(), 0, pocketData, PROTOBUF_START.length() + 2 + bytes.length, PROTOBUF_END.length());
            return pocketData;
        }
    }

}
