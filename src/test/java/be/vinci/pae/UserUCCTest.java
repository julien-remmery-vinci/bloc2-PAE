package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        () -> assertNull(userUCC.login("wrongEmail", password)),
        () -> assertNull(userUCC.login(null, password)),
        () -> assertNull(userUCC.login(email, null)),
        () -> assertNull(userUCC.login("", password)),
        () -> assertNull(userUCC.login(email, "")),
        () -> assertNull(userUCC.login(" ", password)),
        () -> assertNull(userUCC.login(email, " "))
    );
    assertAll(
        () -> assertEquals(user.getIdUser(), testUser.getIdUser()),
        () -> assertEquals(user.getEmail(), testUser.getEmail()),
        () -> assertEquals(user.getPassword(), testUser.getPassword()),
        () -> assertEquals(user.getFirstname(), testUser.getFirstname()),
        () -> assertEquals(user.getLastname(), testUser.getLastname()),
        () -> assertEquals(user.getPhoneNumber(), testUser.getPhoneNumber()),
        () -> assertEquals(user.getRole(), testUser.getRole()),
        () -> assertEquals(user.getRegisterDate(), testUser.getRegisterDate())
    );
  }
}
