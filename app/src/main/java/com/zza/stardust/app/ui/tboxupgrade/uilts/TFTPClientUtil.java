package com.zza.stardust.app.ui.tboxupgrade.uilts;

import com.zza.stardust.app.ui.tboxupgrade.api.callback.TransFileCallBack;

import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPClient;
import org.apache.commons.net.tftp.TFTPPacket;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * TFTP使用的是apache的net库，源码经过修改，添加OPTION，不再判断recdPort == port等
 */
public class TFTPClientUtil {

    private boolean closed;
    private int transferMode = TFTP.BINARY_MODE;

    private TFTPClient tftp = null;
    private int timeout = 5 * 1000;
    private boolean verbose = true;
    private int currentNum = 0;
    //private Context context;

    public void initClient(final TransFileCallBack callBack, final int totle) {
        //this.context = context;
        // Create our TFTP instance to handle the file transfer.
        currentNum = 0;
        if (verbose) {
            tftp = new TFTPClient() {
                @Override
                protected void trace(String direction, TFTPPacket packet) {
                    currentNum++;
                    //System.out.println(direction + " " + packet);
                    int progress = (int) (((currentNum * 1.0) / totle) * 100);
                    callBack.onTrans(direction, packet.toString(), progress);
                }
            };
        } else {
            tftp = new TFTPClient();
        }
        // We want to timeout if a response takes longer than 60 seconds

        tftp.setDefaultTimeout(timeout);
        // We haven't closed the local file yet.
        closed = false;
    }


    public void sendFile(final String hostname, final String localFilename, final String remoteFilename) throws Exception {
        // We're sending a file
        if (tftp == null) {
            System.err.println("Error: please first init client.");
        } else {
            closed = send(transferMode, hostname, localFilename, remoteFilename, tftp);
        }
        if (!closed) {
            System.out.println("Failed");
        }
        System.out.println("OK");
    }

    public void getFile(String hostname, String localFilename, String remoteFilename) throws Exception{
        // We're sending a file
        if (tftp == null) {
            System.err.println("Error: please first init client.");
        } else {
            closed = receive(transferMode, hostname, localFilename, remoteFilename, tftp);
        }
        if (!closed) {
            System.out.println("Failed");
        }
        System.out.println("OK");
    }

    public void getFiles(String hostname, String localDir, ArrayList<String> remoteFilenames) throws Exception{
        // We're sending a file
        if (tftp == null) {
            System.err.println("Error: please first init client.");
        } else {
            for (int i = 0; i < remoteFilenames.size(); i++) {
                String filePath = remoteFilenames.get(i);
                String[] fileSplite = filePath.split("/");

                closed = receive(transferMode, hostname, localDir + fileSplite[fileSplite.length - 1], filePath, tftp);

//                if (context != null) {
//                    if (!closed) {
//                        //((TFTPActivity) context).updataText("[" + fileSplite[fileSplite.length - 1] + "]Failed");
//                    } else {
//                        //((TFTPActivity) context).updataText("[" + fileSplite[fileSplite.length - 1] + "]Success: " + localDir + fileSplite[fileSplite.length - 1] + "");
//                    }
//                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static boolean send(int transferMode, String hostname, String localFilename, String remoteFilename,
                                TFTPClient tftp) throws Exception {
        boolean closed;
        FileInputStream input = null;

        // Try to open local file for reading
        try {
            input = new FileInputStream(localFilename);
        } finally {
            tftp.close();
        }

        open(tftp);

        // Try to send local file via TFTP
        try {
            String[] parts = hostname.split(":");
            if (parts.length == 2) {
                tftp.sendFile(remoteFilename, transferMode, input, parts[0], Integer.parseInt(parts[1]));
            } else {
                tftp.sendFile(remoteFilename, transferMode, input, hostname);
            }
        } finally {
            // Close local socket and input file
            closed = close(tftp, input);
        }

        return closed;
    }

    private static boolean receive(int transferMode, String hostname, String localFilename, String remoteFilename,
                                   TFTPClient tftp) throws Exception{
        boolean closed;
        boolean result = true;
        FileOutputStream out = null;

        // Try to open local file for reading
        try {
            out = new FileOutputStream(localFilename);
        } catch (IOException e) {
            tftp.close();
            System.err.println("Error: could not open local file for reading.");
            System.err.println(e.getMessage());
            result = false;
            return result;
        }

        open(tftp);

        // Try to send local file via TFTP
        try {
            String[] parts = hostname.split(":");
            if (parts.length == 2) {
                tftp.receiveFile(remoteFilename, transferMode, out, parts[0], Integer.parseInt(parts[1]));
            } else {
                tftp.receiveFile(remoteFilename, transferMode, out, hostname);
            }
        } finally {
            // Close local socket and input file
            closed = close(tftp, out);
        }

        return result;
    }

    private static void open(TFTPClient tftp) throws Exception {
        tftp.open();
    }


    private static boolean close(TFTPClient tftp, Closeable output) throws Exception {
        boolean closed;
        tftp.close();

        if (output != null) {
            output.close();
        }

        closed = true;

        return closed;
    }

}
