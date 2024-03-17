package be.vinci.pae.business.contact;

import be.vinci.pae.business.user.UserDTO;
import java.util.List;

/**
 * Interface of ContactUCCImpl.
 */
public interface ContactUCC {

  /**
   * Get the contact.
   *
   * @return the contact
   */
  List<ContactDTO> getContacts(UserDTO user);

  ContactDTO refuseContact(int id, String refusalReason, int idUser);
}
