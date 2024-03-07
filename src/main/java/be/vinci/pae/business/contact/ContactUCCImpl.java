package be.vinci.pae.business.contact;

import be.vinci.pae.dal.contact.ContactDAO;
import jakarta.inject.Inject;

/**
 * Implementation of ContactUCC.
 */
public class ContactUCCImpl implements ContactUCC {

  @Inject
  private ContactDAO contactDAO;

  /**
   * Refuse a contact.
   *
   * @param id the id of the contact
   * @return the contact
   */
  @Override
  public ContactDTO refuseContact(int id, String refusalReason) {
    return contactDAO.refuseContact(id, refusalReason);
  }
}
