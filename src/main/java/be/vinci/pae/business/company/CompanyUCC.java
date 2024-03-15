package be.vinci.pae.business.company;

import java.util.List;

/**
 * Interface of CompanyUCCImpl.
 */
public interface CompanyUCC {

  /**
   * Get all the companies.
   *
   * @return all a list of companies
   */
  List<CompanyDTO> getAll();
}
