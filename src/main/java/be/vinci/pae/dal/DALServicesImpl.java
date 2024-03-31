package be.vinci.pae.dal;

import be.vinci.pae.presentation.exceptions.FatalException;
import be.vinci.pae.utils.Config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Class implementing the DALServices and DALBackServices interfaces.
 */
public class DALServicesImpl implements DALBackServices, DALServices {

  private final BasicDataSource basicDataSource;
  private ThreadLocal<Connection> threadLocal;

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
        throw new FatalException(e);
      }
    }
    return conn;
  }

  @Override
  public PreparedStatement getPS(String request) {
    try {
      return getConnection().prepareStatement(request);
    } catch (SQLException e) {
      throw new FatalException(e);
    }
  }

  @Override
  public void start() {
    try {
      getConnection().setAutoCommit(false);
    } catch (SQLException e) {
      throw new FatalException(e);
    }
  }

  @Override
  public void close() {
    Connection conn = getConnection();
    try {
      if (conn != null && !conn.isClosed()) {
        conn.close();
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    } finally {
      threadLocal.remove();
    }
  }

  @Override
  public void commit() {
    Connection conn = getConnection();
    try {
      conn.commit();
    } catch (SQLException e) {
      throw new FatalException(e);
    } finally {
      try {
        conn.setAutoCommit(true);
      } catch (SQLException e) {
        throw new FatalException(e);
      } finally {
        close();
      }
    }
  }

  @Override
  public void rollback() {
    Connection conn = getConnection();
    try {
      conn.rollback();
    } catch (SQLException e) {
      throw new FatalException(e);
    } finally {
      try {
        conn.setAutoCommit(true);
      } catch (SQLException e) {
        throw new FatalException(e);
      } finally {
        close();
      }
    }
  }

}