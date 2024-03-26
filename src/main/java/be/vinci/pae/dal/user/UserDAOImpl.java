package be.vinci.pae.dal.user;

import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.dal.DALBackServices;
import be.vinci.pae.dal.utils.DAOServices;
import jakarta.inject.Inject;
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
  private DALBackServices dalBackServices;
  @Inject
  private DAOServices daoServices;

  @Override
  public UserDTO getOneByEmail(String email) {
    try (PreparedStatement getUser = dalBackServices.getPS(
        "SELECT * FROM pae.users WHERE user_email = ?")) {
      getUser.setString(1, email);
      try (ResultSet rs = getUser.executeQuery()) {
        if (rs.next()) {
          return (UserDTO) daoServices.getDataFromRs(rs, "user");
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
    try (PreparedStatement getUser = dalBackServices.getPS(
        "SELECT * FROM pae.users WHERE user_idUser = ?")) {
      getUser.setInt(1, id);
      try (ResultSet rs = getUser.executeQuery()) {
        if (rs.next()) {
          return (UserDTO) daoServices.getDataFromRs(rs, "user");
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  @Override
  public UserDTO addUser(UserDTO user) {
    try (PreparedStatement addUser = dalBackServices.getPS(
        "INSERT INTO pae.users (user_lastname, user_firstname, "
            + "user_email, user_password, user_phoneNumber, user_registerDate,"
            + " user_role, user_academicYear, user_version) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) "
            + "RETURNING user_idUser")) {
      setPs(addUser, user);
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
    try (PreparedStatement getUsers = dalBackServices.getPS(
        "SELECT * FROM pae.users")) {
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
      users.add((UserDTO) daoServices.getDataFromRs(rs, "user"));
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
    try (PreparedStatement updateUser = dalBackServices.getPS(
        "UPDATE pae.users SET user_lastname = ?, user_firstname = ?,"
            + " user_email = ?, user_password = ?, user_phoneNumber = ?,"
            + " user_registerDate = ?, user_role = ?, user_academicYear = ?, "
            + "user_version ? WHERE user_idUser = ?"
            + " AND user_version = ? RETURNING user_idUser")) {
      setPs(updateUser, user);
      updateUser.setInt(9, user.getVersion() + 1);
      updateUser.setInt(10, user.getIdUser());
      updateUser.setInt(11, user.getVersion());
      updateUser.executeUpdate();
      try (ResultSet rs = updateUser.executeQuery()) {
        if (!rs.next()) {
          if (getOneById(user.getIdUser()).getVersion() != user.getVersion()) {
            throw new RuntimeException("Version mismatch");
          }
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return user;
  }

  /**
   * Set the PreparedStatement.
   *
   * @param ps   the PreparedStatement
   * @param user the user
   * @throws SQLException if an error occurs while setting the PreparedStatement
   */
  private void setPs(PreparedStatement ps, UserDTO user) throws SQLException {
    ps.setString(1, user.getLastname());
    ps.setString(2, user.getFirstname());
    ps.setString(3, user.getEmail());
    ps.setString(4, user.getPassword());
    ps.setString(5, user.getPhoneNumber());
    ps.setDate(6, user.getRegisterDate());
    ps.setString(7, user.getRole().getRole());
    ps.setString(8, user.getAcademicYear());
  }

}
