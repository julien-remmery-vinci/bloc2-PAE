package be.vinci.pae.business.contact;

import be.vinci.pae.business.contact.ContactDTO.State;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.user.UserDTO.Role;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.company.CompanyDAO;
import be.vinci.pae.dal.contact.ContactDAO;
import be.vinci.pae.dal.user.UserDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;
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
    Contact contact = (Contact) contactDAO.getOneById(idContact);

    if (contact == null) {
      return null;
    }
    if (contact.getIdStudent() != idUser) {
      throw new WebApplicationException("You don't have a contact with this id",
          Status.NOT_FOUND);
    }
    if (!contact.getState().equals(State.ADMITTED)) {
      throw new WebApplicationException("The contact must be in the state 'taken' to be refused",
          Status.PRECONDITION_FAILED);
    }
    contact.setState(State.TURNED_DOWN);
    contact.setRefusalReason(refusalReason);
    contactDAO.updateContact(contact);
    dalServices.close();
    return contact;
  }

  @Override
  public ContactDTO addContact(ContactDTO contact) {
    if (companyDAO.getCompanyById(contact.getIdCompany()) == null) {
      throw new WebApplicationException("The company does not exist", Status.NOT_FOUND);
    }
    // TODO : check if the student hasn't already a contact accepted
    dalServices.start();
    contact.setState(State.STARTED);
    contact = contactDAO.addContact(contact);
    dalServices.commit();
    return contact;
  }

  @Override
  public ContactDTO meetContact(int id, String meetPlace, int idUser) {
    Contact contact = (Contact) contactDAO.getOneById(id);
    if (contact == null) {
      return null;
    }
    if (contact.getIdStudent() != idUser) {
      throw new WebApplicationException("You don't have a contact with this id",
          Status.NOT_FOUND);
    }
    if (!contact.getState().equals(State.STARTED)) {
      throw new WebApplicationException("The contact must be in the state 'initiated' to be met",
          Status.PRECONDITION_FAILED);
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
      throw new WebApplicationException("You don't have a contact with this id",
          Status.NOT_FOUND);
    }
    if (!contact.getState().equals(State.STARTED) || !contact.getState()
        .equals(State.ADMITTED)) {
      throw new WebApplicationException(
          "The contact must be either in the state 'initiated' or 'taken' to be unfollowed",
          Status.PRECONDITION_FAILED);
    }
    contact.setState(State.UNSUPERVISED);
    contactDAO.updateContact(contact);
    return contact;
  }
}
