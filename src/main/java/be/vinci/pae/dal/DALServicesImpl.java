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

  /**
   * Constructor of DALServicesImpl.
   */
  public DALServicesImpl() {
    basicDataSource = new BasicDataSource();

    String url = Config.getProperty("DB_URL");
    String username = Config.getProperty("DB_USER");
    String password = Config.getProperty("DB_PASSWORD");
    basicDataSource.setUrl(url);
    basicDataSource.setUsername(username);
    basicDataSource.setPassword(password);
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
  public void start() {
    threadLocal = new ThreadLocal<>();
    try {
      getConnection().setAutoCommit(false);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void commit() {
    try {
      getConnection().commit();
      getConnection().setAutoCommit(true);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    try {
      basicDataSource.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void rollback() {
    try {
      getConnection().rollback();
      getConnection().setAutoCommit(true);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    try {
      basicDataSource.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}
