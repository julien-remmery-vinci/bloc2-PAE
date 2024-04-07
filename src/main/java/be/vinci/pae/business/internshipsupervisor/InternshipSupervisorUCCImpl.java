package be.vinci.pae.business.internshipsupervisor;

import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.company.CompanyDAO;
import be.vinci.pae.dal.internshipsupervisor.InternshipSupervisorDAO;
import be.vinci.pae.presentation.exceptions.NotFoundException;
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

  @Inject
  private CompanyDAO companyDAO;

  @Override
  public List<InternshipSupervisorDTO> getSupervisorsByCompanyID(int idCompany) {
    try {
      dalServices.open();
      CompanyDTO company = companyDAO.getCompanyById(idCompany);
      if (company == null) {
        throw new NotFoundException("Company not found");
      }
      return internshipSupervisorDAO.getSupervisorsByCompanyID(idCompany);
    } finally {
      dalServices.close();
    }
  }
}
