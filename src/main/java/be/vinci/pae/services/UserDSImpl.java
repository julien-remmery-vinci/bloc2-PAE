package be.vinci.pae.services;

import be.vinci.pae.business.Factory;
import be.vinci.pae.business.UserDTO;
import be.vinci.pae.utils.Config;
import jakarta.inject.Inject;

import java.sql.*;

/**
 * Implementation of UserDS.
 */
public class UserDSImpl implements UserDS {

  @Inject
  private Factory factory;
  private static PreparedStatement getUser;
  private static Connection conn = null;

  static {
    String url = Config.getProperty("DB_URL");
    String username = Config.getProperty("DB_USER");
    String password = Config.getProperty("DB_PASSWORD");

    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    try {
      conn = DriverManager.getConnection(url, username, password);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    try {
      getUser = conn.prepareStatement("SELECT * from pae.users WHERE email = ?");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public UserDTO getOneByEmail(String email) {
    UserDTO user = factory.getUser();
    try {
      getUser.setString(1, email);
      try (ResultSet rs = getUser.executeQuery()) {
        while (rs.next()) {
          user.setIdUser(rs.getInt(1));
          user.setLastname(rs.getString(2));
          user.setFirstname(rs.getString(3));
          user.setEmail(rs.getString(4));
          user.setPassword(rs.getString(5));
          user.setPhoneNumber(rs.getString(6));
          user.setRegisterDate(rs.getDate(7));
          user.setRole(UserDTO.Role.valueOf(rs.getString(8)));
          return user;
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

}
