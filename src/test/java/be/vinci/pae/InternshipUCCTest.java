package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.vinci.pae.business.Factory;
import be.vinci.pae.business.internship.InternshipDTO;
import be.vinci.pae.business.internship.InternshipUCC;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.dal.internship.InternshipDAO;
import be.vinci.pae.exceptions.NotFoundException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * InternshipUCC test class.
 */
public class InternshipUCCTest {

  static ServiceLocator locator;
  private static InternshipUCC internshipUCC;
  private static InternshipDAO internshipDAO;
  private static Factory factory;
  InternshipDTO internship;

  UserDTO user;

  @BeforeAll
  static void beforeAll() {
    locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    internshipUCC = locator.getService(InternshipUCC.class);
    factory = locator.getService(Factory.class);
    internshipDAO = locator.getService(InternshipDAO.class);
  }

  @BeforeEach
  void setUp() {
    internship = (InternshipDTO) factory.getInternship();
    internship.setIdInternship(1);
    user = (UserDTO) factory.getUser();
    user.setIdUser(1);
    user.setRole(UserDTO.Role.STUDENT);
  }

  @Test
  @DisplayName("Test get internship by id method when the internship don't exists")
  void testGetInternshipNoInternships() {
    Mockito.when(internshipDAO.getInternshipById(user.getIdUser())).thenReturn(new ArrayList<>());
    assertThrows(NotFoundException.class, () -> internshipUCC.getInternshipById(user));
  }

  @Test
  @DisplayName("Test get internship by id method should not return null")
  void testGetInternship() {
    Date date = new Date(System.currentTimeMillis());
    internship.setSignatureDate(date);
    List<InternshipDTO> internships = new ArrayList<>();
    internships.add(internship);
    Mockito.when(internshipDAO.getInternshipById(user.getIdUser())).thenReturn(internships);
    assertNotNull(internshipUCC.getInternshipById(user), "Internship should not be null");
  }

  @Test
  @DisplayName("Test get internship by id method for the current year")
  void testGetInternshipByCurrentYear() {
    List<InternshipDTO> internships = new ArrayList<>();
    InternshipDTO internshipLastYear = (InternshipDTO) factory.getInternship();
    internshipLastYear.setSignatureDate(Date.valueOf(LocalDate.now().minusYears(1)));
    internships.add(internshipLastYear);
    InternshipDTO internshipThisYear = (InternshipDTO) factory.getInternship();
    internshipThisYear.setSignatureDate(Date.valueOf(LocalDate.now()));
    internships.add(internshipThisYear);

    Mockito.when(internshipDAO.getInternshipById(user.getIdUser())).thenReturn(internships);

    InternshipDTO result = internshipUCC.getInternshipById(user);
    assertNotNull(result, "Internship should not be null");
    assertEquals(internshipThisYear.getSignatureDate(), result.getSignatureDate(),
        "Internship of the current year should be returned");
  }
}
