package be.vinci.pae.business.company;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.contact.ContactDTO.State;
import be.vinci.pae.business.contact.ContactUCC;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.company.CompanyDAO;
import be.vinci.pae.presentation.exceptions.NotFoundException;
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

  @Inject
  private ContactUCC contactUCC;

  /**
   * Get all companies.
   *
   * @return all companies
   */
  public List<CompanyDTO> getAll() {
    List<CompanyDTO> companies = companyDAO.getAll();
    dalServices.close();
    return companies;
  }

  /**
   * Get a company by its id.
   *
   * @param id the id of the company
   * @return the company, null if no company was found
   */
  public CompanyDTO getCompanyById(int id) {
    CompanyDTO company = companyDAO.getCompanyById(id);
    dalServices.close();
    return company;
  }

  @Override
  public CompanyDTO blacklistCompany(int idCompany, String reason) {
    try {
      dalServices.start();
      CompanyDTO company = companyDAO.getCompanyById(idCompany);
      if (company == null) {
        throw new NotFoundException("Company not found");
      }
      company.setBlacklisted(true);
      company.setBlacklistMotivation(reason);
      companyDAO.updateCompany(company);
      for (ContactDTO c : contactUCC.getContactsByCompany(idCompany)) {
        if (c.getState().equals(State.STARTED) || c.getState().equals(State.ADMITTED)) {
          contactUCC.blacklistContact(c);
        }
      }
      dalServices.commit();
      return company;
    } catch (Exception e) {
      dalServices.rollback();
      throw e;
    } finally {
      dalServices.close();
    }
  }
}
