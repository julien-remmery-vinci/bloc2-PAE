package be.vinci.pae.business.contact;

import be.vinci.pae.business.academicyear.AcademicYear;
import be.vinci.pae.business.contact.ContactDTO.State;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.user.UserDTO.Role;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.company.CompanyDAO;
import be.vinci.pae.dal.contact.ContactDAO;
import be.vinci.pae.dal.user.UserDAO;
import be.vinci.pae.presentation.exceptions.NotFoundException;
import be.vinci.pae.presentation.exceptions.PreconditionFailedException;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Implementation of ContactUCC.
 */
public class ContactUCCImpl implements ContactUCC {

  @Inject
  private ContactDAO contactDAO;

  @Inject
  private DALServices dalServices;

  @Inject
  private CompanyDAO companyDAO;

  @Inject
  private UserDAO userDAO;

  @Inject
  private AcademicYear academicYear;

  /**
   * Get the contact.
   *
   * @return the contact
   */
  @Override
  public List<ContactDTO> getContacts(UserDTO user) {
    if (user == null) {
      return null; //error message
    }
    if (user.getRole().equals(Role.STUDENT)) {
      return contactDAO.getContactsByStudentId(user.getIdUser());
    }
    if (user.getRole().equals(Role.ADMIN) || user.getRole().equals(Role.PROFESSOR)) {
      return contactDAO.getAllContacts();
    }
    return null; //error message
  }


  /**
   * Refuse a contact.
   *
   * @param idContact     the id of the contact
   * @param refusalReason the refusal reason
   * @param idUser        the id of the user
   * @return the contact
   */
  @Override
  public ContactDTO refuseContact(int idContact, String refusalReason, int idUser) {
    ContactDTO contact = contactDAO.getOneById(idContact);

    if (contact == null) {
      return null;
    }
    if (contact.getIdStudent() != idUser) {
      throw new NotFoundException("You don't have a contact with this id");
    }
    if (!((Contact) contact).updateState(State.TURNED_DOWN)) {
      throw new PreconditionFailedException(
          "Le contact doit être dans l'état 'pris' pour être refusé");
    }
    contact.setRefusalReason(refusalReason);
    contactDAO.updateContact(contact);
    dalServices.close();
    return contact;
  }

  @Override
  public ContactDTO addContact(ContactDTO contact) {
    if (companyDAO.getCompanyById(contact.getIdCompany()) == null) {
      throw new NotFoundException("The company does not exist");
    }
    if (contactDAO.getContactAccepted(contact.getIdStudent()) != null) {
      throw new PreconditionFailedException("You already have a contact accepted");
    }
    contact.setAcademicYear(academicYear.getAcademicYear());
    if (contactDAO.getCompanyContact(contact.getIdStudent(), contact.getIdCompany(),
        contact.getAcademicYear()) != null) {
      throw new PreconditionFailedException(
          "You already have a contact with that company for the current year");
    }
    contact.setState(State.STARTED);
    contact = contactDAO.addContact(contact);
    return contact;
  }

  @Override
  public ContactDTO meetContact(int id, String meetPlace, int idUser) {
    Contact contact = (Contact) contactDAO.getOneById(id);
    if (contact == null) {
      return null;
    }
    if (contact.getIdStudent() != idUser) {
      throw new NotFoundException("You don't have a contact with this id");
    }
    if (!contact.updateState(State.ADMITTED)) {
      throw new PreconditionFailedException(
          "The contact must be in the state 'started' to be admitted");
    }
    contact.setState(State.ADMITTED);
    contact.setMeetPlace(meetPlace);
    contactDAO.updateContact(contact);
    dalServices.close();
    return contact;
  }


  @Override
  public ContactDTO unfollowContact(int id, int idUser) {
    Contact contact = (Contact) contactDAO.getOneById(id);
    if (contact == null) {
      return null;
    }
    if (contact.getIdStudent() != idUser) {
      throw new NotFoundException("You don't have a contact with this id");
    }
    if (!contact.updateState(State.UNSUPERVISED)) {
      throw new PreconditionFailedException(
          "The contact must be either in the state 'started' or 'admitted' to be unsupervised");
    }
    contact.setState(State.UNSUPERVISED);
    contactDAO.updateContact(contact);
    return contact;
  }
}
