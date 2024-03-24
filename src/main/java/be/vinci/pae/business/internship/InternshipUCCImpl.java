package be.vinci.pae.business.internship;

import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.internship.InternshipDAO;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Implementation of InternshipUCC.
 */
public class InternshipUCCImpl implements InternshipUCC {

  @Inject
  private DALServices dalServices;

  @Inject
  private InternshipDAO internshipDAO;

  /**
   * Get an internship by its id.
   *
   * @param user the user
   * @return the internship
   */
  @Override
  public List<InternshipDTO> getInternshipById(UserDTO user) {
    return internshipDAO.getInternshipById(user.getIdUser());
  }
}
