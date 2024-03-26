package be.vinci.pae.business.internship;

import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.internship.InternshipDAO;
import be.vinci.pae.presentation.exceptions.NotFoundException;
import jakarta.inject.Inject;
import java.time.LocalDate;

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
  public InternshipDTO getInternshipById(UserDTO user) {
    return internshipDAO.getInternshipById(user.getIdUser()).stream()
        .filter(
            internship -> internship.getSignatureDate().toLocalDate().getYear() == LocalDate.now()
                .getYear()).findFirst()
        .orElseThrow(() -> new NotFoundException("Internship not found for the current year"));
  }
}
