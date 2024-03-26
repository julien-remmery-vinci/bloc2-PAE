package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.vinci.pae.business.Factory;
import be.vinci.pae.business.contact.Contact;
import be.vinci.pae.business.contact.ContactDTO.State;
import be.vinci.pae.business.contact.ContactUCC;
import be.vinci.pae.dal.contact.ContactDAO;
import be.vinci.pae.presentation.exceptions.NotFoundException;
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

  private final int idContact = 1;
  private final int idUser = 1;
  private final String refusalReason = "refusalReason";
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
    contact.setIdContact(idContact);
    contact.setIdStudent(idUser);
    contact.setState(State.ADMITTED);
    Mockito.when(contactDAO.getOneById(1)).thenReturn(contact);
  }

  @Test
  @DisplayName("Test refuseContact with contact in wrong state")
  void testRefuseContactWrongState() {
    contact.setState(State.TURNED_DOWN);
    assertThrows(WebApplicationException.class,
        () -> contactUCC.refuseContact(idContact, refusalReason, idUser));
  }

  @Test
  @DisplayName("Test refuseContact with contact that doesn't belong to the user")
  void testRefuseContactWrongUser() {
    contact.setIdStudent(2);
    assertThrows(NotFoundException.class,
        () -> contactUCC.refuseContact(idContact, refusalReason, idUser));
  }

  @Test
  @DisplayName("Test refuseContact with contact not found")
  void testRefuseContactNotFound() {
    Mockito.when(contactDAO.getOneById(1)).thenReturn(null);
    assertNull(contactUCC.refuseContact(idContact, refusalReason, idUser));
  }

  @Test
  @DisplayName("Test refuseContact with existing contact in right state")
  void refuseContactTest() {
    contactUCC.refuseContact(idContact, refusalReason, idUser);
    assertAll(
        () -> assertEquals(State.TURNED_DOWN, contact.getState()),
        () -> assertEquals(refusalReason, contact.getRefusalReason())
    );
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
    contact.setState(State.ADMITTED);
    assertThrows(WebApplicationException.class,
        () -> contactUCC.meetContact(idContact, "meetPlace", idUser));
  }

  @Test
  @DisplayName("Test meetContact with contact not found")
  void testMeetContactNotFound() {
    Mockito.when(contactDAO.getOneById(1)).thenReturn(null);
    assertNull(contactUCC.meetContact(idContact, "meetPlace", idUser));
  }

  @Test
  @DisplayName("Test meetContact with existing contact in right state")
  void testMeetContact() {
    contact.setState(State.STARTED);
    assertAll(
        () -> assertEquals(contact.getIdContact(),
            contactUCC.meetContact(idContact, "meetPlace", idUser).getIdContact()),
        () -> assertEquals(State.ADMITTED, contact.getState()),
        () -> assertEquals("meetPlace", contact.getMeetPlace())
    );
  }
}
