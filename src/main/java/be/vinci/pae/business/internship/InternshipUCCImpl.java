package be.vinci.pae.business.internship;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.contact.ContactUCC;
import be.vinci.pae.business.internshipsupervisor.InternshipSupervisorDTO;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.contact.ContactDAO;
import be.vinci.pae.dal.internship.InternshipDAO;
import be.vinci.pae.dal.internshipsupervisor.InternshipSupervisorDAO;
import be.vinci.pae.presentation.exceptions.NotFoundException;
import jakarta.inject.Inject;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Implementation of InternshipUCC.
 */
public class InternshipUCCImpl implements InternshipUCC {

  @Inject
  private DALServices dalServices;

  @Inject
  private InternshipDAO internshipDAO;

  @Inject
  private InternshipSupervisorDAO internshipSupervisorDAO;

  @Inject
  private ContactDAO contactDAO;

  @Inject
  private ContactUCC contactUCC;

  /**
   * Get an internship by its id.
   *
   * @param user the user
   * @return the internship
   */
  @Override
  public InternshipDTO getInternshipById(UserDTO user) {
    try {
      dalServices.open();
      InternshipDTO result = internshipDAO.getInternshipById(user.getIdUser()).stream()
          .filter(
              internship -> internship.getSignatureDate().toLocalDate().getYear() == LocalDate.now()
                  .getYear()).findAny()
          .orElseThrow(() -> new NotFoundException("Internship not found for the current year"));
      return result;
    } finally {
      dalServices.close();
    }

  }

  @Override
  public InternshipDTO addInternship(InternshipDTO internship) {
    try {
      dalServices.start();
      InternshipSupervisorDTO internshipSupervisor = internshipSupervisorDAO.
          getInternshipSupervisorById(internship.getIdInternshipSupervisor());
      if (internshipSupervisor == null) {
        throw new NotFoundException("Internship supervisor not found");
      }
      if (internshipDAO.getInternshipByStudentId(internship.getIdStudent()) != null) {
        throw new NotFoundException("You already have an internship");
      }
      Date registerDate = new Date(System.currentTimeMillis());
      internship.setSignatureDate(registerDate);
      contactUCC.acceptContact(internship.getIdContact(), internship.getIdStudent());
      internship = internshipDAO.addInternship(internship);
      ContactDTO contact = contactDAO.getOneById(internship.getIdContact());
      internship.setContact(contact);
      InternshipSupervisorDTO supervisor = internshipSupervisorDAO
          .getInternshipSupervisorById(internship.getIdInternshipSupervisor());
      internship.setInternshipSupervisor(supervisor);
      return internship;
    } catch (Exception e) {
      dalServices.rollback();
      throw e;
    } finally {
      dalServices.commit();
    }
  }
}
