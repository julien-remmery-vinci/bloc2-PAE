package be.vinci.pae.dal.company;

import be.vinci.pae.business.company.CompanyDTO;
import java.util.List;

/**
 * Interface of CompanyDAOImpl.
 */
public interface CompanyDAO {

  /**
   * Get all companies.
   *
   * @return all companies
   */
  List<CompanyDTO> getAll();

  /**
   * Get a company by its id.
   *
   * @param id the id of the company
   * @return the company, null if no company was found
   */
  CompanyDTO getCompanyById(int id);

  /**
   * Update a company.
   *
   * @param company the company to update
   */
  CompanyDTO updateCompany(CompanyDTO company);
}
