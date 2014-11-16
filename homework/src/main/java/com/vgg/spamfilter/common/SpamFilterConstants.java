package com.vgg.spamfilter.common;

/**
 *
 */
public class SpamFilterConstants {
    public static final String NEW_LINE = "\n";

    //looping client
    public static final String LOOPING_CLIENT = "CLIENTB";
    public static final long SLEEP_TIME = 60 * 1000; // in milliseconds
    public static final int MAX_LOOPING_CLIENTS = 12;

    //bigram client
    public static final String BIGRAM_CLIENT = "CLIENTA";
    public static final String CLIENT_EOF  = "BIGRAM_CLIENT_EOF";
    public static final String SERVER_EOF  = "BIGRAM_SERVER_EOF";

    //server
    //thread pool settings and objects
    public static final int CORE_POOL_SIZE = 4; //25% of the threads are always running
    public static final int MAX_POOL_SIZE = 12; //remaining threads get launched as needed
    public static final int KEEP_ALIVE_TIME = 60; //in seconds. if thread idle for more than a minute stop and recreate later if necessary


    //
    public static final int DEFAULT_PORT = 9000;
    public static final int SOCKET_TIMEOUT = 60 * 1000; // in milliseconds
}
