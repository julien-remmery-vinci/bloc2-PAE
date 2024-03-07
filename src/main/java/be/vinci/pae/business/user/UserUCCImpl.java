package be.vinci.pae.business.user;

import be.vinci.pae.dal.user.UserDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
    String role;
    if (!user.getEmail()
        .matches("^[a-zA-Z0-9._%+-]+\\.[a-zA-Z0-9._%+-]+@(vinci\\.be|student\\.vinci\\.be)$")) {
      throw new WebApplicationException("email is not valid", Response.Status.BAD_REQUEST);
    }
    if (user.getEmail().matches("^[a-zA-Z0-9._%+-]+\\.[a-zA-Z0-9._%+-]+@student\\.vinci\\.be$")) {
      role = "E";
    } else {
      role = user.getRole().toString();
      if (!role.equals("A") && !role.equals("P")) {
        throw new WebApplicationException("role is not valid", Response.Status.BAD_REQUEST);
      }
    }
    user.setRole(UserDTO.Role.valueOf(role));
    UserDTO userFound = userDAO.getOneByEmail(user.getEmail());
    if (userFound != null) {
      return null;
    }
    user = userDAO.addUser(user);
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
}
