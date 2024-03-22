package be.vinci.pae.business.contact;

<<<<<<< HEAD
import be.vinci.pae.business.user.UserDTO;
import java.util.List;
=======
import jakarta.ws.rs.WebApplicationException;
>>>>>>> a8fbbafe9f1fc62fdae6db8461afc5723426beaa

/**
 * Interface of ContactUCCImpl.
 */
public interface ContactUCC {

  /**
<<<<<<< HEAD
   * Get the contact.
   *
   * @return the contact
   */
  List<ContactDTO> getContacts(UserDTO user);

=======
   * This method is used to get a contact by its id.
   *
   * @param id     the id of the contact
   * @param idUser the id of the user
   * @param refusalReason the reason of the refusal
   * @return the contact if it exists and the conditions are met, null otherwise
   * @throws WebApplicationException if the id of the student does not match the id of the user
   */
>>>>>>> a8fbbafe9f1fc62fdae6db8461afc5723426beaa
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

  ContactDTO unfollowContact(int id, int idUser);
}
