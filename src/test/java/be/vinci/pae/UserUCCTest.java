package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import be.vinci.pae.business.Factory;
import be.vinci.pae.business.UserDTO;
import be.vinci.pae.business.UserUCC;
import be.vinci.pae.dal.UserDAO;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mockito;

public class UserUCCTest {

  private UserUCC userUCC;
  private UserDAO userDAO;
  private Factory factory;

  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    this.userUCC = locator.getService(UserUCC.class);
    this.factory = locator.getService(Factory.class);
    this.userDAO = locator.getService(UserDAO.class);
  }

  @Test
  @DisplayName("Test for the login method of UserUCC")
  void loginTest() {
    UserDTO user = factory.getUser();
    String password = "admin";
    String email = "admin@vinci.be";
    user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
    Mockito.when(userDAO.getOneByEmail(email)).thenReturn(user);
    assertNotNull(userUCC.login(email, password));
  }
}
