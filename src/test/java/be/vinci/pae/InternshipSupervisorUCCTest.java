package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.vinci.pae.business.Factory;
import be.vinci.pae.business.company.Company;
import be.vinci.pae.business.internshipsupervisor.InternshipSupervisor;
import be.vinci.pae.business.internshipsupervisor.InternshipSupervisorUCC;
import be.vinci.pae.dal.company.CompanyDAO;
import be.vinci.pae.dal.internshipsupervisor.InternshipSupervisorDAO;
import be.vinci.pae.exceptions.NotFoundException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * InternshipSupervisorUCC test class.
 */
public class InternshipSupervisorUCCTest {

  static Factory factory;
  private static InternshipSupervisorUCC internshipSupervisorUCC;
  private static InternshipSupervisorDAO internshipSupervisorDAO;
  private static CompanyDAO companyDAO;
  InternshipSupervisor internshipSupervisor;
  Company company;

  @BeforeAll
  static void beforeAll() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    internshipSupervisorUCC = locator.getService(InternshipSupervisorUCC.class);
    factory = locator.getService(Factory.class);
    internshipSupervisorDAO = locator.getService(InternshipSupervisorDAO.class);
    companyDAO = locator.getService(CompanyDAO.class);
  }

  @BeforeEach
  void setUp() {
    internshipSupervisor = (InternshipSupervisor) factory.getInternshipSupervisor();
    internshipSupervisor.setIdInternshipSupervisor(1);
    company = (Company) factory.getCompany();
    company.setIdCompany(1);
  }

  @Test
  @DisplayName("Test get supervisors by company id method when company not found")
  void testGetSupervisorsByCompanyIDCompanyNotFound() {
    Mockito.when(companyDAO.getCompanyById(1)).thenReturn(null);
    assertThrows(NotFoundException.class,
        () -> internshipSupervisorUCC.getSupervisorsByCompanyID(1));
  }

  @Test
  @DisplayName("Test get supervisors by company id")
  void testGetSupervisorsByCompanyID() {
    Mockito.when(companyDAO.getCompanyById(1)).thenReturn(company);
    assertNotNull(internshipSupervisorUCC.getSupervisorsByCompanyID(1));
  }

}
