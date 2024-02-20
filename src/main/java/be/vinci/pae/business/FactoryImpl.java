package be.vinci.pae.business;

/**
 * Implementation of Factory used to create the implementation of the interfaces.
 */
public class FactoryImpl implements Factory {

  @Override
  public UserDTO getUser() {
    return new UserImpl();
  }
}
