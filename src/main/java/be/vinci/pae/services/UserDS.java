package be.vinci.pae.services;

import be.vinci.pae.business.UserDTO;

/**
 * Interface of UserDSImpl
 */
public interface UserDS {

  /**
   * Get one user from email
   *
   * @param email the email of the user
   * @return the user found, null if no user was found
   */
  UserDTO getOneByEmail(String email);
}
