package com.zza.stardust.app.ui.TFTP;

import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPClient;
import org.apache.commons.net.tftp.TFTPPacket;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class TFTPSendFileClient {

    private boolean closed;
    private int transferMode = TFTP.BINARY_MODE;

    private TFTPClient tftp = null;
    private int timeout = 10000;
    private boolean verbose = true;

    public void initClient() {
        // Create our TFTP instance to handle the file transfer.
        if (verbose) {
            tftp = new TFTPClient() {
                @Override
                protected void trace(String direction, TFTPPacket packet) {
                    System.out.println(direction + " " + packet);
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

    public void sendFile(String hostname, String localFilename, String remoteFilename){
        // We're sending a file
        if (tftp == null){
            System.err.println("Error: please first init client.");
        }else {
            closed = send(transferMode, hostname, localFilename, remoteFilename, tftp);
        }
        if (!closed) {
            System.out.println("Failed");
        }
        System.out.println("OK");
    }

    private static boolean send(int transferMode, String hostname, String localFilename, String remoteFilename,
                                TFTPClient tftp) {
        boolean closed;
        FileInputStream input = null;

        // Try to open local file for reading
        try
        {
            input = new FileInputStream(localFilename);
        }
        catch (IOException e)
        {
            tftp.close();
            System.err.println("Error: could not open local file for reading.");
            System.err.println(e.getMessage());
            System.exit(1);
        }

        open(tftp);

        // Try to send local file via TFTP
        try
        {
            String [] parts = hostname.split(":");
            if (parts.length == 2) {
                tftp.sendFile(remoteFilename, transferMode, input, parts[0], Integer.parseInt(parts[1]));
            } else {
                tftp.sendFile(remoteFilename, transferMode, input, hostname);
            }
        }
        catch (UnknownHostException e)
        {
            System.err.println("Error: could not resolve hostname.");
            System.err.println(e.getMessage());
        }
        catch (IOException e)
        {
            System.err.println("Error: I/O exception occurred while sending file.");
            System.err.println(e.getMessage());
        }
        finally
        {
            // Close local socket and input file
            closed = close(tftp, input);
        }

        return closed;
    }

    private static void open(TFTPClient tftp) {
        try
        {
            tftp.open();
        }
        catch (SocketException e)
        {
            System.err.println("Error: could not open local UDP socket.");
            System.err.println(e.getMessage());
        }
    }


    private static boolean close(TFTPClient tftp, Closeable output) {
        boolean closed;
        tftp.close();
        try
        {
            if (output != null) {
                output.close();
            }
            closed = true;
        }
        catch (IOException e)
        {
            closed = false;
            System.err.println("Error: error closing file.");
            System.err.println(e.getMessage());
        }
        return closed;
    }

}
