package be.vinci.pae.dal.internship;

import be.vinci.pae.business.internship.InternshipDTO;

/**
 * Interface of InternshipDAOImpl.
 */
public interface InternshipDAO {

  /**
   * Get an internship by its id.
   *
   * @param id
   * @return the internship
   */
  InternshipDTO getInternshipById(int id);

}
