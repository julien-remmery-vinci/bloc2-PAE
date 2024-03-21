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

  CompanyDTO getCompanyById(int id);
}
