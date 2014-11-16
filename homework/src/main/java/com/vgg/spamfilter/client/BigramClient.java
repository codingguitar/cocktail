package com.vgg.spamfilter.client;



import com.vgg.spamfilter.common.SpamFilterConstants;

import java.io.*;

/**
 *
 */
public class BigramClient extends Client{

    private String fileName;
    private String response;

    BigramClient(){}

    public void start(){
            try {
                //send header info to server
                sendMessage(SpamFilterConstants.BIGRAM_CLIENT);
            } catch (IOException e) {
                System.out.println("Failed to send header message over socket");
                e.printStackTrace();
            }

            sendAndReceive();
    }

    public boolean isValidFile() {
        boolean isValid = true;

        File file = new File(fileName);
        if (file.length() <= 0){
            isValid = false;
            System.out.println("Ensure " + fileName + " exists and is not a directory");
        }
        return isValid;
    }

    public void sendAndReceive() {

        try {
            //read the parsed fileName and send to server
            sendFile(fileName);

            //signal end of file to server thread
            sendMessage(SpamFilterConstants.CLIENT_EOF);

            //set socket timeout, in case server does not respond
            setSocketTimeout(SpamFilterConstants.SOCKET_TIMEOUT);

            //read the response back from server
            String data;
            while ((data = readMessage()) != null && !data.equals(SpamFilterConstants.SERVER_EOF)){
                response = data;
                System.out.println(data);
            }

            //close socket
            close();
        } catch (IOException e) {
            System.out.println("Failed to write on socket");
            e.printStackTrace();
        }
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getResponse() {
        return response;
    }

    private static void printUsage() {
        System.out.println("Following usages supported");
        System.out.println("Usage : java -cp <jar> com.netbase.client.BigramClient <filename>");
        System.out.println("Usage : java -cp <jar> com.netbase.client.BigramClient <host> <port> <filename>");
        System.exit(0);
    }

    public static void main(String[] args){
        BigramClient client = new BigramClient();
        if (args.length >= 1){
            if (args.length == 1){
                client.setFileName(args[0]);
            }
            else if (args.length == 3){
                client.setHost(args[0]);
                if (args[1].matches("\\d+")) {
                    client.setPort(Integer.parseInt(args[1]));
                }
                else {
                    System.out.println("Port should be an integer. Default assigned<" + args[1] + ">");
                }
                client.setFileName(args[2]);
            }
            else {
                printUsage();
            }
            if (client.isValidFile() && client.connect()) {
                client.start();
            }
        }
        else {
            printUsage();
        }
    }
}
