package be.vinci.pae.dal.user;

import be.vinci.pae.business.Factory;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.user.UserImpl;
import be.vinci.pae.dal.DALServices;
import jakarta.inject.Inject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of UserDAO.
 */
public class UserDAOImpl implements UserDAO {

  @Inject
  private Factory factory;
  @Inject
  private DALServices dalServices;

  public UserDTO getUserFromRs(ResultSet rs) {
    UserDTO user = factory.getUser();
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
              rs.getString("user." + f.getName())));
        } else {
          m.invoke(user, rs.getObject("user." + f.getName()));
        }
      } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException |
               ClassNotFoundException | SQLException e) {
        throw new RuntimeException(e);
      }
    }
    return user.getIdUser() == 0 ? null : user;
  }

  @Override
  public UserDTO getOneByEmail(String email) {
    try (PreparedStatement getUser = dalServices.getPS(
        "SELECT idUser, lastname, firstname, email, password, phoneNumber, registerDate, role "
            + "FROM pae.users WHERE email = ?")) {
      getUser.setString(1, email);
      try (ResultSet rs = getUser.executeQuery()) {
        if (rs.next()) {
          return getUserFromRs(rs);
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
          "SELECT idUser, lastname, firstname, email, password, phoneNumber, registerDate, role "
              + "FROM pae.users WHERE idUser = ?");
      getUser.setInt(1, id);
      try (ResultSet rs = getUser.executeQuery()) {
        if (rs.next()) {
          return getUserFromRs(rs);
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  /**
   * Register a user.
   *
   * @param user the user to register
   * @return the registered user
   */
  public UserDTO addUser(UserDTO user) {
    try (PreparedStatement addUser = dalServices.getPS(
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

  /**
   * Fetches all users from the database. This method prepares a SQL statement to fetch all users
   * from the database. It then executes the statement and processes the result set by calling the
   * getResults method.
   *
   * @return a list of UserDTO objects representing all users in the database
   * @throws RuntimeException if a SQLException is caught
   */
  public List<UserDTO> getAllUsers() {
    try (PreparedStatement getUsers = dalServices.getPS(
        "SELECT idUser, lastname, firstname, email, password, phoneNumber, registerDate, role "
            + "FROM pae.users")) {
      try (ResultSet rs = getUsers.executeQuery()) {
        return getResults(rs);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Processes a ResultSet to create a list of UserDTO objects. This method iterates over the rows
   * in the given ResultSet. For each row, it creates a new UserDTO object, populates it with the
   * data from the row, and adds it to a list. The list of UserDTO objects is then returned.
   *
   * @param rs the ResultSet to process
   * @return a list of UserDTO objects representing the users in the ResultSet
   * @throws SQLException if an error occurs while processing the ResultSet
   */
  private List<UserDTO> getResults(ResultSet rs) throws SQLException {
    List<UserDTO> users = new ArrayList<>();
    while (rs.next()) {
      users.add(getUserFromRs(rs));
    }
    return users;
  }

  /**
   * Update a user in the database.
   *
   * @param user the user to update
   * @return the user updated
   */
  public UserDTO updateUser(UserDTO user) {
    try (PreparedStatement updateUser = dalServices.getPS(
        "UPDATE pae.users SET lastname = ?, firstname = ?,"
            + " email = ?, password = ?, phoneNumber = ?,"
            + " registerDate = ?, role = ? WHERE idUser = ? RETURNING idUser")) {
      updateUser.setString(1, user.getLastname());
      updateUser.setString(2, user.getFirstname());
      updateUser.setString(3, user.getEmail());
      updateUser.setString(4, user.getPassword());
      updateUser.setString(5, user.getPhoneNumber());
      updateUser.setDate(6, user.getRegisterDate());
      updateUser.setString(7, user.getRole().toString());
      updateUser.setInt(8, user.getIdUser());
      try (ResultSet rs = updateUser.executeQuery()) {
        if (rs.next()) {
          return user;
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return null;
  }
}
