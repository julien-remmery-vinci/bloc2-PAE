package be.vinci.pae.business.contact;

/**
 * Interface of ContactImpl inherits of ContactDTO.
 */
public interface Contact extends ContactDTO {

  /**
   * Update the state of the contact.
   *
   * @param state the new state of the contact
   */
  boolean updateState(State state);
}
