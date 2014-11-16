package com.vgg.spamfilter.client;

import com.vgg.spamfilter.common.SpamFilterConstants;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 */
public class Client {
    private static final String NEW_LINE = "\n";

    private Socket clientSocket;
    private String host = "localhost";
    private int port = SpamFilterConstants.DEFAULT_PORT;

    private DataOutputStream outputStream = null;
    private BufferedReader inputStreamReader = null;


    Client(){
    }

    public boolean connect(){
        boolean initStatus = true;
        try {
            clientSocket = new Socket(host, port);
            outputStream = new DataOutputStream(clientSocket.getOutputStream());
            inputStreamReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Failed to connect to server");
            initStatus = false;
        }
        return initStatus;
    }

    public void sendMessage(String message) throws IOException {
        if (outputStream != null) {
            outputStream.writeBytes(message);
            outputStream.writeBytes(SpamFilterConstants.NEW_LINE);
            outputStream.flush();
        }
        else {
            throw new IOException("Outputstream for client socket invalid");
        }
    }

    public String readMessage() throws IOException {
        return inputStreamReader.readLine();
    }

    public void sendFile(String fileName) throws IOException {
        String data = new String(Files.readAllBytes(Paths.get(fileName)), "UTF-8");
        sendMessage(data);
    }

    public void close() throws IOException {
        clientSocket.close();
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setSocketTimeout(int soTimeout) throws SocketException {
        clientSocket.setSoTimeout(soTimeout);
    }
}
