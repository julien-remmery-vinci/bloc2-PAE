package be.vinci.pae.dal;

import be.vinci.pae.utils.Config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Class implementing the DALServices and DALBackServices interfaces.
 */
public class DALServicesImpl implements DALBackServices, DALServices {

  private ThreadLocal<Connection> threadLocal;
  private final BasicDataSource basicDataSource;

  /**
   * Constructor of DALServicesImpl.
   */
  public DALServicesImpl() {
    basicDataSource = new BasicDataSource();
    threadLocal = new ThreadLocal<>();

    String url = Config.getProperty("DB_URL");
    String username = Config.getProperty("DB_USER");
    String password = Config.getProperty("DB_PASSWORD");
    basicDataSource.setUrl(url);
    basicDataSource.setUsername(username);
    basicDataSource.setPassword(password);
  }

  @Override
  public Connection getConnection() {
    Connection conn = threadLocal.get();
    if (conn == null) {
      try {
        conn = basicDataSource.getConnection();
        threadLocal.set(conn);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
    return conn;
  }

  @Override
  public PreparedStatement getPS(String request) {
    try {
      return getConnection().prepareStatement(request);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
  @Override
  public void open() {
    try {
      getConnection().setAutoCommit(false);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void close() {
    try {
      getConnection().setAutoCommit(true);
      getConnection().close();
    } catch (SQLException e) {
      try {
        getConnection().close();
      } catch (SQLException ex) {
        throw new RuntimeException(ex);
      }
      throw new RuntimeException(e);
    }
  }

  @Override
  public void start() {
    open();
  }

  @Override
  public void commit() {
      try {
        getConnection().commit();
        close();
      } catch (SQLException e) {
        close();
        throw new RuntimeException(e);
      }
  }

  @Override
  public void rollback() {
      try {
          getConnection().rollback();
          close();
      } catch (SQLException e) {
          close();
          throw new RuntimeException(e);
      }
  }
}
