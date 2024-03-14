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
   * Register a user.
   *
   * @param user the user to register
   * @return the registered user
   */
  UserDTO register(UserDTO user);

  /**
   * Get a user by its id.
   *
   * @param id the id of the user
   * @return the user, null if no user was found
   */
  UserDTO getUser(int id);

  /**
   * Change the password of a user.
   *
   * @param idUser      the id of the user
   * @param oldPassword the old password of the user
   * @param newPassword the new password of the user
   * @return true if the password was changed, false otherwise
   */
  boolean changePassword(int idUser, String oldPassword, String newPassword);
}
