package be.vinci.pae.business.contact;

/**
 * Interface of ContactUCCImpl.
 */
public interface ContactUCC {

  ContactDTO refuseContact(int id, String refusalReason, int idUser);

  ContactDTO addContact(ContactDTO contact);
  ContactDTO meetContact(int id, String meetPlace, int idUser);
}
