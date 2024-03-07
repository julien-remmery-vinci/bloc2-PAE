package be.vinci.pae.dal.user;

import be.vinci.pae.business.user.UserDTO;

/**
 * Interface of UserDAOImpl.
 */
public interface UserDAO {

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

}
