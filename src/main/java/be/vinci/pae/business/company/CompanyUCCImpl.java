package be.vinci.pae.business.company;

import be.vinci.pae.business.contact.ContactUCC;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.company.CompanyDAO;
import be.vinci.pae.exceptions.ConflictException;
import be.vinci.pae.exceptions.NotFoundException;
import jakarta.inject.Inject;
import java.util.ArrayList;
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

  @Override
  public List<CompanyDTO> getAll() {
    try {
      dalServices.open();
      return companyDAO.getAll();
    } finally {
      dalServices.close();
    }
  }

  @Override
  public CompanyDTO getCompanyById(int id) {
    try {
      dalServices.open();
      return companyDAO.getCompanyById(id);
    } finally {
      dalServices.close();
    }
  }

  @Override
  public List<Object> blacklistCompany(int idCompany, String reason) {
    try {
      dalServices.start();
      CompanyDTO company = companyDAO.getCompanyById(idCompany);
      if (company == null) {
        throw new NotFoundException("L'entreprise n'éxiste pas");
      }
      if (company.isBlacklisted()) {
        throw new ConflictException("L'entreprise est déjà blacklistée");
      }
      company.setBlacklisted(true);
      company.setBlacklistMotivation(reason);
      companyDAO.updateCompany(company);
      ArrayList<Object> list = new ArrayList<>();
      list.add(company);
      list.addAll(contactUCC.blacklistContacts(idCompany));
      return list;
    } catch (Exception e) {
      dalServices.rollback();
      throw e;
    } finally {
      dalServices.commit();
    }
  }

  @Override
  public CompanyDTO addCompany(CompanyDTO company) {
    try {
      dalServices.open();
      List<CompanyDTO> existingCompanies = companyDAO.getCompaniesByName(company.getTradeName());
      for (CompanyDTO existingCompany : existingCompanies) {
        if (existingCompany.getTradeName().equals(company.getTradeName())) {
          throw new ConflictException("L'appellation pour cette entreprise existe déjà");
        }
      }
      if (!existingCompanies.isEmpty()
              && company.getDesignation() == null) {
        throw new ConflictException("L'entreprise existe déjà");
      }
      company = companyDAO.addCompany(company);
      return company;
    } finally {
      dalServices.close();
    }
  }
}
