package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import be.vinci.pae.business.Factory;
import be.vinci.pae.business.UserUCC;
import be.vinci.pae.services.UserDS;
import be.vinci.pae.services.UserDSImpl;
import be.vinci.pae.utils.ApplicationBinder;
import be.vinci.pae.utils.Config;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UserUCCTest {

  static {
    Config.load("dev.properties");
  }

  private UserUCC userUCC;
  private UserDS userDS;
  private Factory factory;

  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinder());

    this.userUCC = locator.getService(UserUCC.class);
    this.factory = locator.getService(Factory.class);

    this.userDS = Mockito.mock(UserDSImpl.class);

    Mockito.when(userDS.getOneByEmail("admin")).thenReturn(factory.getUser());
  }

  @Test
  @DisplayName("Tests for the login method of UserUCC")
  void loginTest() {
    assertNotNull(userUCC.login("admin", "admin"));
  }
}
