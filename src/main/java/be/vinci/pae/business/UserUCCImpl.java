package be.vinci.pae.business;

import be.vinci.pae.dal.UserDAO;
import be.vinci.pae.dal.utils.Json;
import jakarta.inject.Inject;

/**
 * Implementation of UserUCC.
 */
public class UserUCCImpl implements UserUCC {

  private final Json json = new Json<>(UserDTO.class);
  /**
   * Injected UserDAO.
   */
  @Inject
  private UserDAO userDAO;

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
      return (UserDTO) json.filterPublicJsonView(u);
    }

    //Password did not match
    return null;
  }

  /**
   * Get a user by its id.
   *
   * @param id the id of the user
   * @return the user, null if no user was found
   */
  public UserDTO getUser(int id) {
    UserDTO userFound = userDAO.getOneById(id);
    return (UserDTO) json.filterPublicJsonView(userFound);
  }
}
