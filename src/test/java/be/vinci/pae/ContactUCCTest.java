package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.vinci.pae.business.Factory;
import be.vinci.pae.business.academicyear.AcademicYear;
import be.vinci.pae.business.company.Company;
import be.vinci.pae.business.contact.Contact;
import be.vinci.pae.business.contact.ContactDTO.State;
import be.vinci.pae.business.contact.ContactUCC;
import be.vinci.pae.business.user.User;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.user.UserDTO.Role;
import be.vinci.pae.dal.company.CompanyDAO;
import be.vinci.pae.dal.contact.ContactDAO;
import be.vinci.pae.presentation.exceptions.NotFoundException;
import be.vinci.pae.presentation.exceptions.PreconditionFailedException;
import jakarta.ws.rs.WebApplicationException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * The type Contact ucc test.
 */
public class ContactUCCTest {

  static Factory factory;
  private static ContactUCC contactUCC;
  private static ContactDAO contactDAO;
  private static CompanyDAO companyDAO;
  private static AcademicYear academicYear;
  private final int idContact = 1;
  private final int idUser = 1;
  private final String refusalReason = "refusalReason";
  Contact contact;
  Contact contact2;
  Company company;
  User user;

  @BeforeAll
  static void beforeAll() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    contactUCC = locator.getService(ContactUCC.class);
    contactDAO = locator.getService(ContactDAO.class);
    companyDAO = locator.getService(CompanyDAO.class);
    academicYear = locator.getService(AcademicYear.class);
    factory = locator.getService(Factory.class);
  }

  @BeforeEach
  void setUp() {
    contact = (Contact) factory.getContact();
    contact.setIdContact(idContact);
    contact.setIdStudent(idUser);
    contact.setState(State.STARTED);
    Mockito.when(contactDAO.getOneById(1)).thenReturn(contact);

    contact2 = (Contact) factory.getContact();
    contact2.setIdContact(2);
    contact2.setIdStudent(idUser);
    contact2.setIdCompany(1);
    contact2.setAcademicYear("2021-2022");

    company = (Company) factory.getCompany();
    company.setDesignation("company");
    company.setIdCompany(1);

    Mockito.when(companyDAO.getCompanyById(1)).thenReturn(company);

    user = (User) factory.getUser();
    user.setIdUser(idUser);
    user.setRole(UserDTO.Role.STUDENT);
  }

  @Test
  @DisplayName("Test addContact with all conditions met")
  void testAddContact() {
    contact.setIdCompany(1);
    contact.setAcademicYear("2021-2022");
    Mockito.when(contactDAO.addContact(contact)).thenReturn(contact);
    Mockito.when(contactDAO.getContactAccepted(idUser)).thenReturn(null);
    Mockito.when(contactDAO.getCompanyContact(idUser, 1, academicYear.getAcademicYear()))
        .thenReturn(null);
    assertAll(
        () -> assertNotNull(contactUCC.addContact(contact)),
        () -> assertEquals(State.STARTED, contact.getState())
    );
  }

  @Test
  @DisplayName("Test addContact with company already in contact")
  void testAddContactCompanyAlreadyInContact() {
    contact.setIdCompany(1);
    Mockito.when(contactDAO.getCompanyContact(contact.getIdStudent(), contact.getIdCompany(),
        academicYear.getAcademicYear())).thenReturn(contact);
    assertThrows(PreconditionFailedException.class, () -> contactUCC.addContact(contact));
  }

  @Test
  @DisplayName("Test addContact when there is already a contact accepted")
  void testAddContactAlreadyAccepted() {
    contact.setIdCompany(1);
    Mockito.when(contactDAO.getContactAccepted(idUser)).thenReturn(contact);
    assertThrows(PreconditionFailedException.class, () -> contactUCC.addContact(contact));
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
    contact.setState(State.ADMITTED);
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

  @Test
  @DisplayName("Test get list of contacts")
  void testGetContacts() {
    assertNotNull(contactUCC.getContacts(user));
  }

  @Test
  @DisplayName("Test get list of contacts with a null user")
  void testGetContactsNullUser() {
    assertThrows(NotFoundException.class, () -> contactUCC.getContacts(null));
  }

  @Test
  @DisplayName("Test get list of contacts with a student user")
  void testGetContactsProfessorUser() {
    user.setRole(Role.PROFESSOR);
    assertNotNull(contactUCC.getContacts(user));
  }

  @Test
  @DisplayName("Test get list of contacts with a student user")
  void testGetContactsAdminUser() {
    user.setRole(Role.ADMIN);
    assertNotNull(contactUCC.getContacts(user));
  }
}



