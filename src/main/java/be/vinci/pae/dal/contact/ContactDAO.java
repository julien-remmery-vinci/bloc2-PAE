package be.vinci.pae.dal.contact;

import be.vinci.pae.business.contact.ContactDTO;
import java.sql.ResultSet;
import java.util.List;

/**
 * Interface of ContactDAOImpl.
 */
public interface ContactDAO {

  /**
   * Get a contact from a ResultSet.
   *
   * @param rs the ResultSet
   * @return the contact
   */
  ContactDTO getContactFromRs(ResultSet rs);

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
  void updateContact(ContactDTO contact);

  /**
   * Get the contacts of a student.
   *
   * @param idStudent the id of the student
   * @return the contacts
   */
  List<ContactDTO> getContactsByStudentId(int idStudent);

  /**
   * Get all the contacts.
   *
   * @return the contacts
   */
  List<ContactDTO> getAllContacts();
}
