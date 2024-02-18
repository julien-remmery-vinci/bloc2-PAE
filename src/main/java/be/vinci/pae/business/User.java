package be.vinci.pae.business;

/**
 * Interface of UserImpl inherits of UserDTO
 */
public interface User extends UserDTO {

  /**
   * Check the password
   *
   * @param password the password to check
   * @return true if it matches, false otherwise
   */
  boolean checkPassword(String password);

  /**
   * Hash the password
   *
   * @param password the password to hash
   * @return hashed password
   */
  String hashPassword(String password);
}
