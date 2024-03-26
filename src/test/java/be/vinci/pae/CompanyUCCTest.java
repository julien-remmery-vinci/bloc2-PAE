package be.vinci.pae;

import be.vinci.pae.business.Factory;
import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.company.CompanyUCC;
import be.vinci.pae.dal.company.CompanyDAO;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

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

//  @Test
//  @DisplayName("Test for the getCompanyById method of CompanyUCC with a correct id")
//  void getCompanyByIdTest() {
//    Mockito.when(companyDAO.getCompanyById(1)).thenReturn(company);
//    assertEquals(company.getIdCompany(), companyUCC.getCompanyById(1).getIdCompany());
//  }


}
