package be.vinci.pae.business;

import be.vinci.pae.services.UserDS;
import jakarta.inject.Inject;

public class UserUCCImpl implements UserUCC {

  @Inject
  private UserDS userDS;

  @Override
  public UserDTO login(String email, String password) {
    UserDTO userFound = userDS.getOneByEmail(email);
    return null;
  }

}
