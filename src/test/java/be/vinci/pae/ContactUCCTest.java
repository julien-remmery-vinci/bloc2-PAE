package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.vinci.pae.business.Factory;
import be.vinci.pae.business.contact.Contact;
import be.vinci.pae.business.contact.ContactUCC;
import be.vinci.pae.dal.contact.ContactDAO;
import jakarta.ws.rs.WebApplicationException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * The type Contact ucc test.
 */
public class ContactUCCTest {

  Contact contact;
  private ContactUCC contactUCC;
  private ContactDAO contactDAO;
  private Factory factory;
  private ServiceLocator locator;

  @BeforeEach
  void setUp() {
    locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    contactUCC = locator.getService(ContactUCC.class);
    contactDAO = locator.getService(ContactDAO.class);
    factory = locator.getService(Factory.class);

    contact = (Contact) factory.getContact();
    contact.setIdContact(1);
    contact.setState(Contact.STATE_TAKEN);
    Mockito.when(contactDAO.getOneById(1)).thenReturn(contact);
  }

  @Test
  @DisplayName("Test refuseContact with contact in wrong state")
  void testRefuseContactWrongState() {
    contact.setState(Contact.STATE_TAKENDOWN);
    assertThrows(WebApplicationException.class, () -> contactUCC.refuseContact(1, "refusalReason"));
  }

  @Test
  @DisplayName("Test refuseContact with contact not found")
  void testRefuseContactNotFound() {
    Mockito.when(contactDAO.getOneById(1)).thenReturn(null);
    assertNull(contactUCC.refuseContact(1, "refusalReason"));
  }

  @Test
  @DisplayName("Test refuseContact with existing contact in right state")
  void testGetOneById() {
    String state = "refusalReason";
    assertEquals(contact.getIdContact(),
        contactUCC.refuseContact(1, state).getIdContact());
    assertEquals(Contact.STATE_TAKENDOWN, contact.getState());
    assertEquals(state, contact.getRefusalReason());
  }
}
