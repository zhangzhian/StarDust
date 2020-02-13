package com.zza.stardust.app.ui.tboxprotobuf.control;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zza.library.utils.BytesUtils;
import com.zza.library.utils.LogUtil;
import com.zza.stardust.app.ui.tboxprotobuf.IVITboxProto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
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
    private static final int ERROR = -99;

    private static final int SEND_PERIOD = -99;
    private static final String PROTOBUF_START = "#START*";
    private static final String PROTOBUF_END = "#END*";

    private volatile static ProtobufControlImpl uniqueInstance;

    private ProtobufListener listener = null;
    private Thread threadTcp = null;
    private Thread threadSend = null;
    private Boolean isRunning = true;

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
            threadTcp.start();
        } else {
            if (listener != null) listener.onConnectFail(ERR_NOT_INIT, new Exception("not init."));
        }

        if (threadSend != null) threadSend.start();
    }

    @Override
    public void disConnect() {
        //4.关闭资源
        try {

            buffList.clear();

            if (is != null)
                is.close();
            if (os != null)
                os.close();
            if (socket != null)
                socket.close();
            //if (pw != null)
            //   pw.close();

            isRunning = false;

            if (threadTcp != null) threadTcp = null;

            if (threadSend != null) threadSend = null;

            if (listener != null) listener.onDisConnect();
        } catch (IOException e) {
            e.printStackTrace();
            if (listener != null) listener.onDisConnectFail(ERR_IO, e);
            isRunning = false;
        }
    }

    @Override
    public void sendCmd(int msgId, String cmd, boolean period, int periodTimeMils) {
        try {
            IVITboxProto.TopMessage topMessage = ProtobufMessageMange.paraseJSONString2Message(cmd, IVITboxProto.TopMessage.newBuilder());
            buffList.add(new ProtoBufEvent(msgId, cmd, topMessage, period, periodTimeMils));
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            if (listener != null) listener.sendDataFail(msgId, period,ERR_INVALID_PROTOCOLBUFFER, e);
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
                byte[] buf = new byte[1024];

                //创建客户端Socket，指定服务器地址和端口
                socket = new Socket(host, port);
                //获取输出流，向服务器端发送信息
                os = socket.getOutputStream();
                //pw = new PrintWriter(os);
                //获取输入流，并读取服务器端的响应信息
                is = socket.getInputStream();

                listener.onConnectSuccess();

                while (isRunning || (is.read(buf)) != -1) {
                    //将字节数组转换成十六进制的字符串
                    String dataStr = BytesUtils.bytesToHexString(buf);
                    LogUtil.w("---->:" + dataStr);
                    paraseReceData(dataStr);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                if (listener != null) listener.onConnectFail(ERR_INTERRUPTED, e);
            } catch (UnknownHostException e) {
                e.printStackTrace();
                if (listener != null) listener.onConnectFail(ERR_UNKNOWN_HOST, e);
            } catch (IOException e) {
                e.printStackTrace();
                if (listener != null) listener.onConnectFail(ERR_IO, e);
            } finally {
                disConnect();
            }

        }

        private void paraseReceData(String dataStr) {
            int startIndex;
            int endIndex;
            int dataLength;
            while ((startIndex = dataStr.indexOf(PROTOBUF_START)) >= 0) {
                dataStr = dataStr.substring(startIndex);
                String lengthData = dataStr.substring(PROTOBUF_START.length(), PROTOBUF_START.length() + 2);
                if (lengthData != null && lengthData.length() == 2) {
                    int length_high = 0;
                    int length_low = 0;
                    try {
                        length_high = Integer.parseInt(lengthData.substring(0, 1));
                        length_low = Integer.parseInt(lengthData.substring(1));
                        dataLength = length_high * 16 + length_low;
                        endIndex = dataStr.indexOf(PROTOBUF_END);
                        if (endIndex > 0 && endIndex - PROTOBUF_START.length() - 2 == dataLength) {
                            dataStr = dataStr.substring(2, dataLength - PROTOBUF_END.length());
                            byte[] dataBuf = BytesUtils.convertHexToBytes(dataStr.getBytes(), dataLength);
                            if (listener != null) {
                                IVITboxProto.TopMessage topMessage = ProtobufMessageMange.paraseBytes2Message(dataBuf);
                                listener.receData(topMessage);
                            }
                        } else {
                            dataStr = dataStr.substring(startIndex + PROTOBUF_START.length());
                            continue;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        dataStr = dataStr.substring(startIndex + PROTOBUF_START.length());
                        continue;
                    }

                } else {
                    return;
                }
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
                        if (!protoBufEvent.getPeriod()) {
                            int times = protoBufEvent.getPeriodTimeMils() / SEND_PERIOD;
                            if (protoBufEvent.getCurTimes() < times) {
                                protoBufEvent.setCurTimes(protoBufEvent.getCurTimes() + 1);
                            } else {
                                protoBufEvent.setCurTimes(0);
                                sendData(protoBufEvent);
                            }
                        } else {
                            sendData(protoBufEvent);
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
                os.write(msg.getTopMessage().toByteArray());
                os.flush();
                if (listener != null) listener.sendDataSuccess(msg.getMsgId(), msg.getPeriod());
            } catch (IOException e) {
                e.printStackTrace();
                if (listener != null)
                    listener.sendDataFail(msg.getMsgId(), msg.getPeriod(), ERR_IO, e);
            }
        }
    }
}
