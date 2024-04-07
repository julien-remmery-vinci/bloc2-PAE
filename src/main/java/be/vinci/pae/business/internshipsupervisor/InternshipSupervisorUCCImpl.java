package be.vinci.pae.business.internshipsupervisor;

import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.internshipsupervisor.InternshipSupervisorDAO;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Implementation of InternshipSupervisorUCC.
 */
public class InternshipSupervisorUCCImpl implements InternshipSupervisorUCC {

  @Inject
  private DALServices dalServices;

  @Inject
  private InternshipSupervisorDAO internshipSupervisorDAO;

  @Override
  public List<InternshipSupervisorDTO> getAll() {
    try {
      dalServices.open();
      return internshipSupervisorDAO.getAll();
    } finally {
      dalServices.close();
    }
  }
}
