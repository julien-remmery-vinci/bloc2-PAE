package be.vinci.pae.business.contact;

import be.vinci.pae.dal.contact.ContactDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;

/**
 * Implementation of ContactUCC.
 */
public class ContactUCCImpl implements ContactUCC {

  @Inject
  private ContactDAO contactDAO;

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
          Status.BAD_REQUEST);
    }
    contact.setState(Contact.STATE_TAKENDOWN);
    contact.setRefusalReason(refusalReason);
    contactDAO.updateContact(contact);
    return contact;
  }
}
