package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import be.vinci.pae.business.Factory;
import be.vinci.pae.business.User;
import be.vinci.pae.business.UserUCC;
import be.vinci.pae.dal.UserDAO;
import java.sql.Date;
import java.time.LocalDate;
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
    user.setIdUser(1);
    user.setEmail(email);
    user.setPassword(user.hashPassword(password));
    user.setFirstname("admin");
    user.setLastname("admin");
    user.setPhoneNumber("123456789");
    user.setRole(User.Role.A);
    user.setRegisterDate(Date.valueOf(LocalDate.now()));
    Mockito.when(userDAO.getOneByEmail(email)).thenReturn(user);
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
}
