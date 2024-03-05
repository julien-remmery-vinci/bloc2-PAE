package be.vinci.pae.business;

import be.vinci.pae.business.user.UserDTO;

/**
 * Interface of FactoryImpl.
 */
public interface Factory {

  /**
   * Create a new UserImpl.
   *
   * @return a new UserImpl
   */
  UserDTO getUser();
}
