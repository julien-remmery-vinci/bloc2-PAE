package be.vinci.pae.business.user;

import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.user.UserDAO;
import be.vinci.pae.presentation.exceptions.BadRequestException;
import jakarta.inject.Inject;
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
    dalServices.close();
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
    if (!((User) user).defineRole(user.getEmail())) {
      throw new BadRequestException("Invalid role");
    }
    dalServices.start();
    UserDTO userFound = userDAO.getOneByEmail(user.getEmail());
    if (userFound != null) {
      dalServices.rollback();
      return null;
    }
    user.setPassword(((User) user).hashPassword(user.getPassword()));
    java.sql.Date registerDate = new java.sql.Date(System.currentTimeMillis());
    user.setRegisterDate(registerDate);
    user.setVersion(1);

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
    UserDTO user = userDAO.getOneById(id);
    dalServices.close();
    return user;
  }

  /**
   * Get all users.
   *
   * @return the list of all users
   */
  public List<UserDTO> getAllUsers() {
    List<UserDTO> list = userDAO.getAllUsers();
    dalServices.close();
    return list;
  }
}
