package be.vinci.pae.business;

/**
 * Interface of UserUCCImpl.
 */
public interface UserUCC {

  /**
   * Try to log in a user.
   *
   * @param email    the email of the user
   * @param password the password of the user
   * @return the logged user, null if no user was found
   */
  UserDTO login(String email, String password);
}
