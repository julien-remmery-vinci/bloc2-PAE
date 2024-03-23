package be.vinci.pae.business.internship;

import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.internship.InternshipDAO;
import jakarta.inject.Inject;

/**
 * Implementation of InternshipUCC.
 */
public class InternshipUCCImpl implements InternshipUCC {

  @Inject
  private DALServices dalServices;

  @Inject
  private InternshipDAO internshipDAO;

  /**
   * Get an internship by its id.
   *
   * @param id the id
   * @return the internship
   */
  @Override
  public InternshipDTO getInternshipById(int id) {
    InternshipDTO internship = internshipDAO.getInternshipById(id);
    dalServices.close();
    return internship;
  }
}
