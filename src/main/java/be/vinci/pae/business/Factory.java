package be.vinci.pae.business;

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
