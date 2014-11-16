package com.vgg.connectionpool;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 */
public class ConnectionPoolImplTest {

  ConnectionPoolImpl pool;
  int liveCheckTimeout = 1;

  @Before
  public void setup(){
    pool = new ConnectionPoolImpl("jdbc:mysql://localhost/test", "", "");
    pool.setMock(true);
    int maxConnections = 3;
    pool.setMaxConnections(maxConnections);
    pool.setLiveCheckTimeout(liveCheckTimeout);
  }

  @Test
  public void testGetConnection() throws Exception {
    //test create multiple and release
    //set up easymock to expect calls on the connection object
//    Connection connection = pool.getConnection();
//    EasyMock.expect(connection.isValid(liveCheckTimeout)).andReturn(true);
//    connection.close();
//    EasyMock.expectLastCall();
//    EasyMock.replay(connection);
//
//    Connection connection1 = pool.getConnection();
//    connection1.close();
//    EasyMock.expectLastCall();
//    EasyMock.expect(connection1.isValid(liveCheckTimeout)).andReturn(true);
//    EasyMock.replay(connection1);
//
//    Assert.assertTrue(pool.getOpenConnections() == 2);
//    Assert.assertTrue(pool.getFreeConnections() == 0);
//    pool.releaseConnection(connection1);
//    Assert.assertTrue(pool.getOpenConnections() == 1);
//    Assert.assertTrue(pool.getFreeConnections() == 1);
//
//    //test reuse of connection. connection1 was just released so it shud be returned here
//    Assert.assertTrue(pool.getConnection() == connection1);
//    //there should be no new connections created
//    Assert.assertTrue(pool.getOpenConnections() == 2);
//    Assert.assertTrue(pool.getFreeConnections() == 0);
  }

  @Test
  public void testMaxConnections() throws Exception {
    pool.getConnection();
    pool.getConnection();
    pool.getConnection();
    //should throw an exception at this point
    try {
      pool.getConnection();
    }
    catch (SQLException sqle){
      Assert.assertTrue(sqle.getMessage().equals(ErrorConstants.MAX_OPEN_ERROR));
    }
  }

  @Test
  public void testReleaseConnection() throws Exception {
    Connection connection = pool.getConnection();
    try{
      pool.releaseConnection(null);
    }
    catch (SQLException sqle){
      Assert.assertTrue(sqle.getMessage().equals(ErrorConstants.INVALID_RELEASE_ERROR));
    }

    pool.releaseConnection(connection);
    Assert.assertTrue(pool.getFreeConnections() == 1);
    Assert.assertTrue(pool.getOpenConnections() == 0);

    //try release on same connection
    try{
      pool.releaseConnection(connection);
    }
    catch (SQLException sqle){
      Assert.assertTrue(sqle.getMessage().equals(ErrorConstants.INVALID_RELEASE_ERROR));
    }
  }

  @Test
  public void testForceShutdown() throws Exception {
    Connection connection = pool.getConnection();
    //try force shutdown with open connections. Should force close
    pool.forceShutdown();
    Assert.assertTrue(pool.getFreeConnections() == 0);
    Assert.assertTrue(pool.getOpenConnections() == 0);
  }

  @Test
  public void testShutdown() throws Exception {
    pool.getConnection();
    //try shutdown with still open connections and expect an exception
    try{
      pool.shutdown();
    }
    catch (SQLException sqle){
      Assert.assertTrue(sqle.getMessage().equals(ErrorConstants.ALIVE_CONNECTIONS_ERROR));
    }
  }

  @After
  public void cleanup(){
    pool.forceShutdown();
  }
}
