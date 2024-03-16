package be.vinci.pae.dal.company;

import be.vinci.pae.business.company.CompanyDTO;
import java.sql.ResultSet;
import java.util.List;

/**
 * Interface of CompanyDAOImpl.
 */
public interface CompanyDAO {

  /**
   * Get a company from a ResultSet.
   *
   * @return the company
   */
  CompanyDTO getCompanyFromRs(ResultSet rs);

  /**
   * Get all companies.
   *
   * @return all companies
   */
  List<CompanyDTO> getAll();

}
