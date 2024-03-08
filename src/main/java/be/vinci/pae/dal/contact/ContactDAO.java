package be.vinci.pae.dal.contact;

import be.vinci.pae.business.contact.ContactDTO;

/**
 * Interface of ContactDAOImpl.
 */
public interface ContactDAO {

  /**
   * Get a contact by its id.
   *
   * @param id the id of the contact
   * @return the contact
   */
  ContactDTO getOneById(int id);

  /**
   * Refuse a contact.
   *
   * @param contact the contact to refuse
   */
  void refuseContact(ContactDTO contact);
}
