package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import be.vinci.pae.business.Factory;
import be.vinci.pae.business.user.User;
import be.vinci.pae.business.user.UserUCC;
import be.vinci.pae.dal.user.UserDAO;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Test class for the UserUCC methods.
 */
public class UserUCCTest {

  ServiceLocator locator;
  User user;
  private UserUCC userUCC;
  private UserDAO userDAO;
  private Factory factory;

  @BeforeEach
  void setUp() {
    locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    this.userUCC = locator.getService(UserUCC.class);
    this.factory = locator.getService(Factory.class);
    this.userDAO = locator.getService(UserDAO.class);

    user = (User) factory.getUser();
    String password = "admin";
    String email = "admin@vinci.be";
    user.setPassword(user.hashPassword(password));
    Mockito.when(userDAO.getOneByEmail(email)).thenReturn(user);
    Mockito.when(userDAO.getOneById(1)).thenReturn(user);
  }

  @Test
  @DisplayName("Test for the login method of UserUCC")
  void loginTest() {
    String password = "admin";
    String email = "admin@vinci.be";
    User testUser = (User) userUCC.login(email, password);
    assertAll(
        () -> assertNotNull(testUser),
        () -> assertNull(userUCC.login(email, "wrongPassword")),
        () -> assertNull(userUCC.login("wrongEmail", password))
    );
  }

  @Test
  @DisplayName("Test for the getUser method of UserUCC")
  void getUserTest() {
    User testUser = (User) userUCC.getUser(1);
    assertAll(
        () -> assertNotNull(testUser),
        () -> assertNull(userUCC.getUser(0)),
        () -> assertNull(userUCC.getUser(-1))
    );
  }
}
