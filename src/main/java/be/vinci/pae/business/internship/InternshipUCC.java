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
  InternshipDTO getInternshipByUser(UserDTO user);

  /**
   * Add an internship.
   *
   * @param internship the internship to add
   * @return the internship added
   */
  InternshipDTO addInternship(InternshipDTO internship);

  /**
   * Update the subject of an internship.
   *
   * @param internship the internship to update
   * @return the internship updated
   */
  InternshipDTO updateInternshipSubject(InternshipDTO internship, String subject);
}
