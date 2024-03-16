package be.vinci.pae.dal;

import be.vinci.pae.utils.Config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Implementation of DALServices.
 */
public class DALBackServicesImpl implements DALBackServices {
  private ThreadLocal <Connection> ds = new ThreadLocal<Connection>();
  private BasicDataSource bds = new BasicDataSource();

  /**
   * Get the connection to the database.
   *
   * @return the connection to the database
   */
  public Connection getConnection() {
    Connection conn = ds.get();
    if (conn == null) {
      try {
        conn = bds.getConnection();
        ds.set(conn);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
    return conn;
  }

  /**
   * Constructor of DALServicesImpl.
   */
  public DALBackServicesImpl() {
    String url = Config.getProperty("DB_URL");
    String username = Config.getProperty("DB_USER");
    String password = Config.getProperty("DB_PASSWORD");

    bds.setUrl(url);
    bds.setUsername(username);
    bds.setPassword(password);
  }

  /**
   * Get the prepared statement with the request.
   *
   * @return the prepared statement with the request.
   */
  public PreparedStatement getPS(String request) {
    try {
      return getConnection().prepareStatement(request);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
