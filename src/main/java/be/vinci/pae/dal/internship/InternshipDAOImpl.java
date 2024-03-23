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

  @Override
  public InternshipDTO getInternshipById(int id) {
    return null;
  }
}
