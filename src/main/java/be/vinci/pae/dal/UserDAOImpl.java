package be.vinci.pae.dal;

import be.vinci.pae.business.Factory;
import be.vinci.pae.business.UserDTO;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of UserDS.
 */
public class UserDAOImpl implements UserDAO {

  @Inject
  private Factory factory;
  @Inject
  private DALServices dalServices;

  @Override
  public UserDTO getOneByEmail(String email) {
    try (PreparedStatement getUser = dalServices.getPS("SELECT * from pae.users WHERE email = ?")){
      getUser.setString(1, email);
      try (ResultSet rs = getUser.executeQuery()) {
        if (rs.next()) {
          UserDTO user = factory.getUser();
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

  /**
   * Get a user by its id.
   *
   * @param id the id of the user
   * @return the user, null if no user was found
   */
  @Override
  public UserDTO getOneById(int id) {
    UserDTO user = factory.getUser();
    try {
      PreparedStatement getUser = dalServices.getPrepareStatement(
          "SELECT * from pae.users WHERE id_user = ?");
      getUser.setInt(1, id);
      try (ResultSet rs = getUser.executeQuery()) {
        if (rs.next()) {
          user.setIdUser(rs.getInt(1));
          user.setLastname(rs.getString(2));
          user.setFirstname(rs.getString(3));
          user.setEmail(rs.getString(4));
          user.setPassword(rs.getString(5));
          user.setPhoneNumber(rs.getString(6));
          user.setRegisterDate(rs.getDate(7));
          user.setRole(UserDTO.Role.valueOf(rs.getString(8)));
          rs.close();
          getUser.close();
          return user;
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

}
