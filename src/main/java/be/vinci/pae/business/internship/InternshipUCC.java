package be.vinci.pae.business.internship;

/**
 * Interface of InternshipUCCImpl.
 */
public interface InternshipUCC {

  /**
   * Get an internship by its id.
   *
   * @param id the id
   * @return the internship
   */
  InternshipDTO getInternshipById(int id);

}
