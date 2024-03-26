package be.vinci.pae.business.internship;

import be.vinci.pae.business.user.UserDTO;

/**
 * Interface of InternshipUCCImpl.
 */
public interface InternshipUCC {

  /**
   * Get an internship by user.
   *
   * @param user the user
   * @return the internship
   */
  InternshipDTO getInternshipById(UserDTO user);

}
