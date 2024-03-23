package be.vinci.pae.dal.internship;

import be.vinci.pae.business.internship.InternshipDTO;
import be.vinci.pae.dal.DALBackServices;
import be.vinci.pae.dal.utils.DAOServices;
import jakarta.inject.Inject;

public class InternshipDAOImpl implements InternshipDAO {

  @Inject
  private DALBackServices dalServices;
  @Inject
  private DAOServices daoServices;

  /**
   * Get an internship by its id.
   *
   * @param id the id
   * @return the internship
   */
  @Override
  public InternshipDTO getInternshipById(int id) {
    return null;
  }
}
