package com.vgg.spamfilter.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.TimerTask;

/**
 * to avoid the looping client hog a thread we will use a timer task to periodically
 * wakeup and print the message. All timer tasks will be run in a single background thread
 */
public class LoopingClientHandler extends TimerTask{

    Socket client;
    BufferedReader reader;
    LoopingClientHandler(Socket client, BufferedReader reader){
        this.client = client;
        this.reader = reader;
    }

    public void run() {
        try {

            if (reader.ready()) {
                String message = reader.readLine();
                System.err.println(message);
            }
        } catch (IOException e) {
            System.out.println("Failed to read from client socket");
        }
    }
}
