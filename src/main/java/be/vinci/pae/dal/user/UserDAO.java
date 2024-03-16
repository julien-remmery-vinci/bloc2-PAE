package be.vinci.pae.dal.user;

import be.vinci.pae.business.user.UserDTO;
import java.sql.ResultSet;
import java.util.List;

/**
 * Interface of UserDAOImpl.
 */
public interface UserDAO {

  /**
   * Get a user from a ResultSet.
   *
   * @param rs the ResultSet
   * @return the user
   */
  UserDTO getUserFromRs(ResultSet rs);

  /**
   * Get one user from email.
   *
   * @param email the email of the user
   * @return the user found, null if no user was found
   */
  UserDTO getOneByEmail(String email);

  /**
   * Get one user from id.
   *
   * @param id the id of the user
   * @return the user found, null if no user was found
   */
  UserDTO getOneById(int id);

  /**
   * Add a user to the database.
   *
   * @param user the user to add
   * @return the user added
   */
  UserDTO addUser(UserDTO user);

  /**
   * Get all users.
   *
   * @return the list of all users
   */
  List<UserDTO> getAllUsers();

}
