package be.vinci.pae.dal.contact;

import be.vinci.pae.business.contact.ContactDTO;

/**
 * Interface of ContactDAOImpl.
 */
public interface ContactDAO {

  ContactDTO refuseContact(int id);
}
