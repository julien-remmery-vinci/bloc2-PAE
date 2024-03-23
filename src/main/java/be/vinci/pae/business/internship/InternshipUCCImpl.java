package be.vinci.pae.business.internship;

import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.internship.InternshipDAO;
import jakarta.inject.Inject;

public class InternshipUCCImpl implements InternshipUCC {

  @Inject
  private DALServices dalServices;

  @Inject
  private InternshipDAO internshipDAO;

  public InternshipDTO getInternshipById(int id) {
    InternshipDTO internship = internshipDAO.getInternshipById(id);
    dalServices.close();
    return internship;
  }
}
