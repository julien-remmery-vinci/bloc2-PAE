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
  public List<ContactDTO> getContact(UserDTO user) {
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
   * @param idContact the id of the contact
   * @return the contact
   */
  @Override
  public ContactDTO refuseContact(int idContact, String refusalReason) {
    Contact contact = (Contact) contactDAO.getOneById(idContact);
    if (contact == null) {
      return null;
    }
    // TODO check if the student matches the user that requested the action

    if (!contact.getState().equals(Contact.STATE_TAKEN)) {
      throw new WebApplicationException("The contact must be in the state 'taken' to be refused",
          Status.PRECONDITION_FAILED);
    }
    contact.setState(Contact.STATE_TAKENDOWN);
    contact.setRefusalReason(refusalReason);
    contactDAO.updateContact(contact);
    return contact;
  }
}
