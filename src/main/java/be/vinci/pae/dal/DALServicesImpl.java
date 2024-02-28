package be.vinci.pae.dal;

import be.vinci.pae.utils.Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DALServicesImpl implements DALServices {

  private static Connection conn = null;

  /**
   * Constructor of DALServicesImpl.
   */
  public DALServicesImpl() {
    String url = Config.getProperty("DB_URL");
    String username = Config.getProperty("DB_USER");
    String password = Config.getProperty("DB_PASSWORD");

    try {
      conn = DriverManager.getConnection(url, username, password);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Get the prepared statement with the request.
   *
   * @return the prepared statement with the request.
   */
  public PreparedStatement getPS(String request) {
    try {
      return conn.prepareStatement(request);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
