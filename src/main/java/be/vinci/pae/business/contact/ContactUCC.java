package be.vinci.pae.business.contact;

import be.vinci.pae.business.user.UserDTO;
import jakarta.ws.rs.WebApplicationException;
import java.util.List;

/**
 * Interface of ContactUCCImpl.
 */
public interface ContactUCC {

  /**
   * This method is used to get a contact by its id.
   *
   * @param id            the id of the contact
   * @param idUser        the id of the user
   * @param refusalReason the reason of the refusal
   * @return the contact if it exists and the conditions are met, null otherwise
   * @throws WebApplicationException if the id of the student does not match the id of the user
   */
  ContactDTO refuseContact(int id, String refusalReason, int idUser);

  /**
   * This method is used to add a contact.
   *
   * @param contact the contact to add
   * @return the contact if it exists and the conditions are met, null otherwise
   * @throws WebApplicationException if the company does not exist
   */
  ContactDTO addContact(ContactDTO contact);

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
  ContactDTO meetContact(int id, String meetPlace, int idUser);

  /**
   * Get the contact.
   *
   * @param user the user
   * @return the contact
   */
  List<ContactDTO> getContacts(UserDTO user);

  /**
   * This method is used to unfollow a contact. It first retrieves the contact by its id.
   *
   * @param id     the id of the contact
   * @param idUser the id of the user
   * @return the contact if it exists and the conditions are met, null otherwise
   * @throws WebApplicationException if the id of the student does not match the id of the user or
   *                                 if the state of the contact is not 'initiated'
   */
  ContactDTO unfollowContact(int id, int idUser);

}
