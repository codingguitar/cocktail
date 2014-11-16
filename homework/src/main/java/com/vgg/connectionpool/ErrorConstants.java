package com.vgg.connectionpool;

/**
 * Created by IntelliJ IDEA.
 * User: girish
 * Date: 12/15/12
 */
public class ErrorConstants {
  //Error messages
  public static final String MAX_OPEN_ERROR = "Server Busy. Too many open connections.";
  public static final String ALIVE_CONNECTIONS_ERROR = "Connection Pool Shutdown attempt with still open connections";
  public static final String INVALID_RELEASE_ERROR = "Connection being released is either null or has already been released";
  public static final String INVALID_DRIVER_CONFIG = "Invalid connection driver configured!";
}
