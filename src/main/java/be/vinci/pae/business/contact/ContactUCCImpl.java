package be.vinci.pae.business.contact;

import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.user.UserDTO.Role;
import be.vinci.pae.dal.contact.ContactDAO;
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
    if (user.getRole().equals(Role.E)) {
      return contactDAO.getContactsByStudentId(user.getIdUser());
    }
    if (user.getRole().equals(Role.A) || user.getRole().equals(Role.P)) {
      return contactDAO.getAllContacts();
    }
    return null;//error message
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
    if (!contact.getState().equals(Contact.STATE_TAKEN)) {
      throw new WebApplicationException("The contact must be in the state 'taken' to be refused",
          Status.PRECONDITION_FAILED);
    }
    contact.setState(Contact.STATE_TAKENDOWN);
    contact.setRefusalReason(refusalReason);
    contactDAO.updateContact(contact);
    return contact;
  }

  /**
   * This method is used to meet a contact. It first retrieves the contact by its id.
   *
   * @param id        the id of the contact
   * @param meetPlace the place to meet the contact
   * @param idUser    the id of the user
   * @return the contact if it exists and the conditions are met, null otherwise
   * @throws WebApplicationException if the id of the student does not match the id of the user or
   *                                 if the state of the contact is not 'initiated'
   */
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
    if (!contact.getState().equals(Contact.STATE_INITIATED)) {
      throw new WebApplicationException("The contact must be in the state 'initiated' to be met",
          Status.PRECONDITION_FAILED);
    }
    contact.setState(Contact.STATE_TAKEN);
    contact.setMeetPlace(meetPlace);
    contactDAO.updateContact(contact);
    return contact;
  }
}
