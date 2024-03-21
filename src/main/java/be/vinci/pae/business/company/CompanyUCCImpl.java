package be.vinci.pae.business.company;

import be.vinci.pae.dal.company.CompanyDAO;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Implementation of CompanyUCC.
 */
public class CompanyUCCImpl implements CompanyUCC {

  @Inject
  private CompanyDAO companyDAO;

  public List<CompanyDTO> getAll() {
    return companyDAO.getAll();
  }

    public CompanyDTO getCompanyById(int id) {
        return companyDAO.getCompanyById(id);
    }
}
