package be.vinci.pae.business.user;

import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.user.UserDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * Implementation of UserUCC.
 */
public class UserUCCImpl implements UserUCC {

  /**
   * Injected UserDAO.
   */
  @Inject
  private UserDAO userDAO;
  /**
   * Injected DALServices.
   */
  @Inject
  private DALServices dalServices;

  /**
   * Login a user.
   *
   * @param email    the email of the user
   * @param password the password of the user
   * @return the user, null if no user was found
   */
  @Override
  public UserDTO login(String email, String password) {
    UserDTO userFound = userDAO.getOneByEmail(email);

    // No user found for the provided email
    if (userFound == null) {
      return null;
    }
    User u = (User) userFound;

    // Check for matching password
    if (u.checkPassword(password)) {
      return u;
    }

    //Password did not match
    return null;
  }

  /**
   * Register a user.
   *
   * @param user the user to register
   * @return the registered user
   */
  public UserDTO register(UserDTO user) {
    if (user.getEmail().matches("^[a-zA-Z0-9._%+-]+\\.[a-zA-Z0-9._%+-]+@student\\.vinci\\.be$")) {
      user.setRole(UserDTO.Role.valueOf("E"));
    } else if (user.getEmail().matches("^[a-zA-Z0-9._%+-]+\\.[a-zA-Z0-9._%+-]+@vinci\\.be$")
        && user.getRole() != null && !user.getRole().toString().equals("A")
        && !user.getRole().toString().equals("P")) {
      throw new WebApplicationException("Invalid role", Response.Status.BAD_REQUEST);
    }
    dalServices.start();
    UserDTO userFound = userDAO.getOneByEmail(user.getEmail());
    if (userFound != null) {
      dalServices.rollback();
      return null;
    }
    java.sql.Date registerDate = new java.sql.Date(System.currentTimeMillis());
    user.setRegisterDate(registerDate);

    user = userDAO.addUser(user);
    dalServices.commit();
    return user;
  }

  /**
   * Get a user by its id.
   *
   * @param id the id of the user
   * @return the user, null if no user was found
   */
  public UserDTO getUser(int id) {
    return userDAO.getOneById(id);
  }

  /**
   * Get all users.
   *
   * @return the list of all users
   */
  public List<UserDTO> getAllUsers() {
    return userDAO.getAllUsers();
  }
}
