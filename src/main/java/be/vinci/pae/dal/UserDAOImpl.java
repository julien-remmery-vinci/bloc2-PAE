package be.vinci.pae.dal;

import be.vinci.pae.business.Factory;
import be.vinci.pae.business.UserDTO;
import be.vinci.pae.business.UserImpl;
import jakarta.inject.Inject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    try (PreparedStatement getUser = dalServices.getPS(
        "SELECT idUser, lastname, firstname, email, password, phoneNumber, registerDate, role FROM pae.users WHERE email = ?")) {
      getUser.setString(1, email);
      try (ResultSet rs = getUser.executeQuery()) {
        if (rs.next()) {
          UserDTO user = factory.getUser();
          getUserFromRs(rs, user);
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
    try {
      PreparedStatement getUser = dalServices.getPS(
          "SELECT idUser, lastname, firstname, email, password, phoneNumber, registerDate, role FROM pae.users WHERE idUser = ?");
      getUser.setInt(1, id);
      try (ResultSet rs = getUser.executeQuery()) {
        if (rs.next()) {
          UserDTO user = factory.getUser();
          getUserFromRs(rs, user);
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
   * @param rs   the result set
   * @param user the user
   * @throws SQLException if an error occurs
   */
  private void getUserFromRs(ResultSet rs, UserDTO user) throws SQLException {
    // Get the fields of the UserImpl class
    for (Field f : UserImpl.class.getDeclaredFields()) {
      try {
        // Get the setter method of the field
        Method m = UserDTO.class.getDeclaredMethod("set" + f.getName().substring(0, 1).toUpperCase()
            + f.getName().substring(1), f.getType());
        // Set the value of the field
        // If the field is of type Role, we need to convert the string to the enum
        if (f.getType().equals(UserDTO.Role.class)) {
          m.invoke(user, UserDTO.Role.valueOf(rs.getString(f.getName())));
        } else {
          m.invoke(user, rs.getObject(f.getName()));
        }
      } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
