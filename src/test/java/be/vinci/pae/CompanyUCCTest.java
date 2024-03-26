package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import be.vinci.pae.business.Factory;
import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.company.CompanyUCC;
import be.vinci.pae.dal.company.CompanyDAO;
import java.util.ArrayList;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * CompanyUCC test class.
 */
public class CompanyUCCTest {

  static ServiceLocator locator;
  private static CompanyUCC companyUCC;
  private static CompanyDAO companyDAO;
  private static Factory factory;
  CompanyDTO company;

  @BeforeAll
  static void beforeAll() {
    locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    companyUCC = locator.getService(CompanyUCC.class);
    factory = locator.getService(Factory.class);
    companyDAO = locator.getService(CompanyDAO.class);
  }

  @BeforeEach
  void setUp() {
    company = (CompanyDTO) factory.getCompany();
    company.setIdCompany(1);

  }

  @Test
  @DisplayName("Test get all companies method when there are companies in the database")
  void testGetAllCompanies() {
    Mockito.when(companyDAO.getAll()).thenReturn(new ArrayList<>());
    assertNotNull(companyUCC.getAll());
  }

  @Test
  @DisplayName("Test get company by id method when the company exists")
  void testGetCompanyById() {
    Mockito.when(companyDAO.getCompanyById(1)).thenReturn(company);
    assertNotNull(companyUCC.getCompanyById(1));
  }


}
