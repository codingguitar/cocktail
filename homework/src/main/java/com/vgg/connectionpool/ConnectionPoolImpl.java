package com.vgg.connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * User: girish
 * Date: 12/15/12
 *
 * Simple Implementation of Connection Pool for a DB Connection.
 */
public class ConnectionPoolImpl implements ConnectionPool{

  //logger instance
  private Logger LOG = Logger.getLogger(getClass().getName());

  //Connection specific params
  private int maxConnections = 10;
  private int liveCheckTimeout = 5;
  private boolean mock = false;
  private String url;
  private Properties dbProperties = new Properties();
  String driver = null;

  /**
   * Using a hashset for keeping track of free connections to check for cases
   * where same connection object may be incorrectly released twice.
   * releaseConnection method handles this
   */
  private HashSet<Connection> openConnections = new HashSet<Connection>();
  private HashSet<Connection> freeConnections = new HashSet<Connection>();
  private Connection current;

  /**
   *
   * @param url       DB connection url
   * @param user      user name credential to use for DB server
   * @param password  password credential to use for DB server
   */
  ConnectionPoolImpl(String url, String user, String password){
    this.url = url;
    this.dbProperties.put("user", user);
    this.dbProperties.put("password", password);
    LOG.log(Level.INFO, "Configured connection params " + url + " user=" + user);
  }

  /**
   *
   * @return Fetches an existing free connection from the pool.
   *          - If there are no free connections and open connections
   *            - are less than max connections create one
   *          - else throw an exception
   *          - clients can wait on pool object in case of no free connections and get notified on release
   *          - keep track of open and free connections
   * @throws SQLException
   */
  public synchronized Connection getConnection() throws SQLException {
    return fetchFromPool();
  }

  private Connection fetchFromPool() throws SQLException {
    if (openConnections.size() >= maxConnections){
      throw new SQLException(ErrorConstants.MAX_OPEN_ERROR);
    }
    //get an existing connection from the free pool
    if (freeConnections.iterator().hasNext()){
      current = freeConnections.iterator().next();
      freeConnections.remove(current);
      //check if the connection is still valid with a configurable timeout value, else create a new one
      if (!current.isValid(liveCheckTimeout)){
        LOG.log(Level.WARNING, "Existing connection died at " + System.currentTimeMillis() + " recreating");
        current.close();
        current = createConnection();
      }
      //keep track of the new open connection
      openConnections.add(current);
    }
    //there were no free connections and we are still under the max connections limit, so create one
    // and keep track of the new open connection
    else {
      current = createConnection();
      openConnections.add(current);
    }

    return current;
  }

  /**
   *
   * @param connection    - connection returned to the pool
   *                      - clients can wait on the pool object and upon a release
   *                      - this method notifies any waiting threads
   * @throws SQLException
   */
  public synchronized void releaseConnection(Connection connection) throws SQLException {
    //checks if the connection object being released is null or there was bad code that
    //is releasing the same connection twice.
    if (connection != null && !freeConnections.contains(connection)){
      freeConnections.add(connection);
      openConnections.remove(connection);
    }
    else{
      if (connection == null){
        LOG.log(Level.WARNING, "Attempt to release null connection");
      }
      else {
        LOG.log(Level.WARNING, "Attempt to release an already free connection");
      }
      throw new SQLException(ErrorConstants.INVALID_RELEASE_ERROR);
    }
    //notify any threads waiting on the pool object for a free connection
    notifyAll();
  }

  /**
   * @return            Creates a mock connection object or actual DB connection based on a flag
   *                    - mock flag is a helper flag for testing
   * @throws SQLException
   */
  private Connection createConnection() throws SQLException {
      if (driver != null){
        try {
          Class.forName(driver).newInstance();
        } catch (Exception e) {
          LOG.log(Level.SEVERE, e.getMessage(), e);
          throw new SQLException(ErrorConstants.INVALID_DRIVER_CONFIG);
        }
      }
      return DriverManager.getConnection(url, dbProperties);
  }

  /**
   * Forces a shutdown irrespective of connection status and ignores all exceptions
   */
  public void forceShutdown() {
    LOG.log(Level.INFO, "Shutdown attempt on connection pool with " + freeConnections.size()
            + " free connections and " + openConnections.size() + " open connections");
    try {
      Iterator<Connection> iter = freeConnections.iterator();
      while (iter.hasNext()){
        iter.next().close();
      }
      freeConnections.clear();
      iter = openConnections.iterator();
      while (iter.hasNext()){
        iter.next().close();
      }
      openConnections.clear();
    } catch (SQLException e) {
      //Ignore exceptions
    }
    LOG.log(Level.INFO, "Shutdown complete on connection pool!");
  }

  /**
   * Shutsdown all existing free connections and throws an exception
   * if there are still open connections. Clients can handle this by
   * waiting.
   * @throws SQLException
   */
  public void shutdown() throws SQLException {
    if (openConnections.size() > 0){
      LOG.log(Level.WARNING, "Shutdown attempt with " + openConnections.size() + " open connections");
      throw new SQLException(ErrorConstants.ALIVE_CONNECTIONS_ERROR);
    }
    forceShutdown();
  }

  //Helper methods to set connection properties to use
  public int getMaxConnections() {
    return maxConnections;
  }

  public void setMaxConnections(int maxConnections) {
    this.maxConnections = maxConnections;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setDriver(String driver) {
    this.driver = driver;
  }

  public Properties getDbProperties() {
    return dbProperties;
  }

  public void setDbProperties(Properties dbProperties) {
    this.dbProperties = dbProperties;
  }

  /**
   * Sets the timeout value when checking for connection being alive
   * @param liveCheckTimeout
   */
  public void setLiveCheckTimeout(int liveCheckTimeout) {
    this.liveCheckTimeout = liveCheckTimeout;
  }

  public void setMock(boolean mock) {
    this.mock = mock;
  }

  public int getOpenConnections() {
    return openConnections.size();
  }

  public int getFreeConnections() {
    return freeConnections.size();
  }

}
