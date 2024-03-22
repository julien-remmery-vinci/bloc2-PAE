package be.vinci.pae.business.company;

import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.company.CompanyDAO;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Implementation of CompanyUCC.
 */
public class CompanyUCCImpl implements CompanyUCC {
  @Inject
  private DALServices dalServices;

  @Inject
  private CompanyDAO companyDAO;

  public List<CompanyDTO> getAll() {
    List<CompanyDTO> companies = companyDAO.getAll();
    dalServices.close();
    return companies;
  }

  public CompanyDTO getCompanyById(int id) {
    CompanyDTO company = companyDAO.getCompanyById(id);
    dalServices.close();
    return company;
  }
}
