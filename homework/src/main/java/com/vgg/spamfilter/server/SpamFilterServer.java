package com.vgg.spamfilter.server;


import com.vgg.spamfilter.common.SpamFilterConstants;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.concurrent.*;

/**
 *
 */
public class SpamFilterServer extends Thread{

    //socket connection related settings and objects
    private int port = SpamFilterConstants.DEFAULT_PORT;
    private boolean stopped = false;
    private ServerSocket serverSocket;

    private BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
    private ExecutorService executor;

    //Create a timer to handle the looping clients in a single background thread
    Timer bgTimerThread = new Timer();
    private int loopingClients = 0;

    public SpamFilterServer(){
    }

    @Override
    public void run(){
        Socket client;
        setupThreadPool();

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Started server on port " + port);
        } catch (IOException e) {
            System.out.println("Failed to open port for server ");
            stopped = true;
        }
        while (!stopped) {
            try {
                client = serverSocket.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

                //parse the message, read the header line to differentiate if the message is from
                //client A or client B
                String header = reader.readLine();
                if (header != null && header.equals(SpamFilterConstants.LOOPING_CLIENT)){
                    //launch a new thread to handle looping client. all the looping clients will be handled by
                    //by a single timer thread in the background . timer is thread safe
                    if (loopingClients < SpamFilterConstants.MAX_LOOPING_CLIENTS) {
                        LoopingClientHandler loopingClientHandler = new LoopingClientHandler(client, reader);
                        bgTimerThread.schedule(loopingClientHandler, 0, SpamFilterConstants.SLEEP_TIME);
                        loopingClients++;
                    }
                    else {
                        String message = "Max looping clients connected! Ignoring new connections";
                        System.out.println(message);
                        client.close();
                    }
                }
                else if (header != null && header.equals(SpamFilterConstants.BIGRAM_CLIENT)){
                    //launch a new thread to calculate bigram probability
                    BigramProbabilityCalculator bigramHandler = new BigramProbabilityCalculator(client, reader);
                    executor.submit(bigramHandler);
                }
                else {
                    System.out.println("Invalid request! Ignore!");
                    client.close();
                }

            } catch (IOException e) {
                stopped = true;
            }
        }
    }

    public void shutdown(){
        System.out.println("Shutdown signal received! Closing resources and initiating shutdown");
        this.setStopped(true);
        if (!executor.isShutdown()) {
            executor.shutdown();
        }
        try {
            if (!serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bgTimerThread.cancel();
        System.out.println("Shutdown complete!");
    }
    private void setupThreadPool() {
        executor = new ThreadPoolExecutor(SpamFilterConstants.CORE_POOL_SIZE, SpamFilterConstants.MAX_POOL_SIZE, SpamFilterConstants.KEEP_ALIVE_TIME, TimeUnit.SECONDS, queue);
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    private static void printUsage() {
        System.out.println("Following usages supported");
        System.out.println("Usage : java -cp <jar> com.netbase.server.SpamFilterServer");
        System.out.println("Usage : java -cp <jar> com.netbase.server.SpamFilterServer <port>");
        System.exit(0);
    }

    public static void main(String[] args){
        final SpamFilterServer server = new SpamFilterServer();
        if (args.length > 0){
            if (args.length == 1) {
                if (args[0].matches("\\d+")) {
                    server.setPort(Integer.parseInt(args[0]));
                } else {
                    System.out.println("Port should be an integer. Default assigned");
                }
            }
            else {
                printUsage();
            }
        }
        server.start();
        BufferedReader reader  = new BufferedReader(new InputStreamReader(System.in));
        String input;
        try {
            while ((input = reader.readLine()) != null){
                input = input.trim();
                if (input.equals("quit") || input.equals("q")){
                    server.shutdown();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            server.shutdown();
        }
    }
}
