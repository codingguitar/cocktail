package com.vgg.spamfilter.client;


import com.vgg.spamfilter.client.Client;
import com.vgg.spamfilter.common.SpamFilterConstants;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 */
public class LoopingClient extends Client {

    private String message = "test message from looping client";

    public void runClient() {
        boolean stopped = false;

        //loop to send the same message every minute
        while (!stopped) {
            try {
                sendMessage(message);
                Thread.sleep(SpamFilterConstants.SLEEP_TIME);
            } catch (IOException e) {
                stopped = true;
                System.out.println("Failed to write on socket output stream");
                System.out.println("Possible reason : Server shutdown or reached max connections and closed connection");
            }
            catch(InterruptedException e) {
                stopped = true;
                System.out.println("Thread interrupted");
            }
        }
    }

    public void start() {
        //send header info to server
        try {
            sendMessage(SpamFilterConstants.LOOPING_CLIENT);
        } catch (IOException e) {
            System.out.println("Failed to send header message over socket");
            e.printStackTrace();
        }

        //process by looping
        runClient();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private static void printUsage() {
        System.out.println("Following usages supported");
        System.out.println("Usage : java -cp <jar> com.netbase.client.LoopingClient");
        System.out.println("Usage : java -cp <jar> com.netbase.client.LoopingClient <message>");
        System.out.println("Usage : java -cp <jar> com.netbase.client.LoopingClient <host> <port>");
        System.out.println("Usage : java -cp <jar> com.netbase.client.LoopingClient <host> <port> <message>");
        System.exit(0);
    }

    public static void main(String[] args){
        LoopingClient client = new LoopingClient();
        if (args.length > 0){
            if (args.length == 1){
                client.setMessage(args[0]);
            }
            else if (args.length >= 2){
                client.setHost(args[0]);
                if (args[1].matches("\\d+")) {
                    client.setPort(Integer.parseInt(args[1]));
                }
                else {
                    System.out.println("Port should be an integer. Default assigned");
                }
                if (args.length >= 3){
                    client.setMessage(args[2]);
                }
            }
            else {
                printUsage();
            }
        }
        if (client.connect()) {
            client.start();
        }
    }

}
