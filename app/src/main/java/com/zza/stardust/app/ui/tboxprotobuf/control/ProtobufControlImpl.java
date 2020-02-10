package com.zza.stardust.app.ui.tboxprotobuf.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

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

    private ProtobufListener listener = null;
    private Thread thread = null;
    private Boolean isRunning = true;

    private Socket socket = null;
    private OutputStream os = null;//字节输出流
    private PrintWriter pw = null;
    private InputStream is = null;

    private String host;
    private int port;

    @Override
    public void connect(String host, int port) {
        this.host = host;
        this.port = port;
        if (thread == null) {
            thread = new Thread(new TcpRunnable());
        }
        thread.start();
    }

    @Override
    public void disConnect() {
        isRunning = false;
    }

    @Override
    public void sendCmd(int msgId, String msg, int times, int period) {

    }

    @Override
    public void stopCmd(int msgId) {

    }

    @Override
    public void addReceDataListener(ProtobufListener listener) {
        this.listener = listener;
    }

    @Override
    public void removeReceDataListener() {
        this.listener = null;
    }


    class TcpRunnable implements Runnable{

        @Override
        public void run(){
            if (isRunning) {
                try {
                    socket = new Socket(host,port);

                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
