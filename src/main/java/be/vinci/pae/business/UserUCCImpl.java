package be.vinci.pae.business;

import be.vinci.pae.services.UserDS;
import jakarta.inject.Inject;

/**
 * Implementation of UserUCC.
 */
public class UserUCCImpl implements UserUCC {

  @Inject
  private UserDS userDS;

  @Override
  public UserDTO login(String email, String password) {
    UserDTO userFound = userDS.getOneByEmail(email);

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

}
