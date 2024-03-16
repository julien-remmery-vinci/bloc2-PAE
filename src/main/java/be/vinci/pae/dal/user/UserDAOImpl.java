package be.vinci.pae.dal.user;

import be.vinci.pae.business.Factory;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.user.UserImpl;
import be.vinci.pae.dal.DALBackServices;
import jakarta.inject.Inject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of UserDAO.
 */
public class UserDAOImpl implements UserDAO {

  @Inject
  private Factory factory;
  @Inject
  private DALBackServices dalBackServices;

  @Override
  public UserDTO getOneByEmail(String email) {
    try (PreparedStatement getUser = dalBackServices.getPS(
        "SELECT idUser, lastname, firstname, email, password, phoneNumber, registerDate, role "
            + "FROM pae.users WHERE email = ?")) {
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
      PreparedStatement getUser = dalBackServices.getPS(
          "SELECT idUser, lastname, firstname, email, password, phoneNumber, registerDate, role "
              + "FROM pae.users WHERE idUser = ?");
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
        Method m = UserDTO.class.getDeclaredMethod(
            "set" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1),
            f.getType());
        // Set the value of the field
        // If the field is of enum type, we need to convert the string to the value in the enum
        if (f.getType().isEnum()) {
          m.invoke(user, Enum.valueOf((Class<Enum>) Class.forName(f.getType().getName()),
              rs.getString(f.getName())));
        } else {
          m.invoke(user, rs.getObject(f.getName()));
        }
      } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException
               | ClassNotFoundException e) {
        throw new RuntimeException(e);
      }
    }
  }

  /**
   * Register a user.
   *
   * @param user the user to register
   * @return the registered user
   */
  public UserDTO addUser(UserDTO user) {
    try (PreparedStatement addUser = dalBackServices.getPS(
        "INSERT INTO pae.users (lastname, firstname, email, password, phoneNumber, registerDate,"
            + " role) " + "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING idUser")) {
      addUser.setString(1, user.getLastname());
      addUser.setString(2, user.getFirstname());
      addUser.setString(3, user.getEmail());
      addUser.setString(4, user.getPassword());
      addUser.setString(5, user.getPhoneNumber());
      addUser.setDate(6, user.getRegisterDate());
      addUser.setString(7, user.getRole().toString());
      try (ResultSet rs = addUser.executeQuery()) {
        if (rs.next()) {
          user.setIdUser(rs.getInt(1));
          return user;
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return null;
  }
}
