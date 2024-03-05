package be.vinci.pae.business.user;

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

  /**
   * Get a user by its id.
   *
   * @param id the id of the user
   * @return the user, null if no user was found
   */
  UserDTO getUser(int id);
}
