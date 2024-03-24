package be.vinci.pae.business.internshipsupervisor;

import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.internship.InternshipDAO;
import jakarta.inject.Inject;

/**
 * Implementation of InternshipSupervisorUCC.
 */
public class InternshipSupervisorUCCImpl implements InternshipSupervisorUCC {

  @Inject
  private DALServices dalServices;

  @Inject
  private InternshipDAO internshipDAO;

  @Override
  public InternshipSupervisorDTO getInternshipSupervisorById(int id) {
    return null;
  }
}
