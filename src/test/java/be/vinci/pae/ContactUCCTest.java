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

  private final int ID_CONTACT = 1;
  private final int ID_USER = 1;
  private final String REFUSAL_REASON = "refusalReason";
  Contact contact;
  private ContactUCC contactUCC;
  private ContactDAO contactDAO;

  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    contactUCC = locator.getService(ContactUCC.class);
    contactDAO = locator.getService(ContactDAO.class);
    Factory factory = locator.getService(Factory.class);

    contact = (Contact) factory.getContact();
    contact.setIdContact(ID_CONTACT);
    contact.setIdStudent(ID_USER);
    contact.setState(Contact.STATE_TAKEN);
    Mockito.when(contactDAO.getOneById(1)).thenReturn(contact);
  }

  @Test
  @DisplayName("Test refuseContact with contact in wrong state")
  void testRefuseContactWrongState() {
    contact.setState(Contact.STATE_TAKENDOWN);
    assertThrows(WebApplicationException.class,
        () -> contactUCC.refuseContact(ID_CONTACT, REFUSAL_REASON, ID_USER));
  }

  @Test
  @DisplayName("Test refuseContact with contact not found")
  void testRefuseContactNotFound() {
    Mockito.when(contactDAO.getOneById(1)).thenReturn(null);
    assertNull(contactUCC.refuseContact(ID_CONTACT, REFUSAL_REASON, ID_USER));
  }

  @Test
  @DisplayName("Test refuseContact with existing contact in right state")
  void testGetOneById() {
    assertEquals(contact.getIdContact(),
        contactUCC.refuseContact(ID_CONTACT, REFUSAL_REASON, ID_USER).getIdContact());
    assertEquals(Contact.STATE_TAKENDOWN, contact.getState());
    assertEquals(REFUSAL_REASON, contact.getRefusalReason());
  }

  @Test
  @DisplayName("Test addContact with company not found")
  void testAddContactCompanyNotFound() {
    contact.setIdCompany(0);
    assertThrows(WebApplicationException.class, () -> contactUCC.addContact(contact));
  }

  @Test
  @DisplayName("Test meetContact with contact in wrong state")
  void testMeetContactWrongState() {
    contact.setState(Contact.STATE_TAKEN);
    assertThrows(WebApplicationException.class,
        () -> contactUCC.meetContact(ID_CONTACT, "meetPlace", ID_USER));
  }

  @Test
  @DisplayName("Test meetContact with contact not found")
  void testMeetContactNotFound() {
    Mockito.when(contactDAO.getOneById(1)).thenReturn(null);
    assertNull(contactUCC.meetContact(ID_CONTACT, "meetPlace", ID_USER));
  }

  @Test
  @DisplayName("Test meetContact with existing contact in right state")
  void testMeetContact() {
    contact.setState(Contact.STATE_INITIATED);
    assertEquals(contact.getIdContact(),
        contactUCC.meetContact(ID_CONTACT, "meetPlace", ID_USER).getIdContact());
    assertEquals(Contact.STATE_TAKEN, contact.getState());
    assertEquals("meetPlace", contact.getMeetPlace());
  }
}
