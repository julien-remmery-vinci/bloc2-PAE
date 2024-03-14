package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import be.vinci.pae.business.Factory;
import be.vinci.pae.business.user.User;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.user.UserUCC;
import be.vinci.pae.dal.user.UserDAO;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Test class for the UserUCC methods.
 */
public class UserUCCTest {

  ServiceLocator locator;
  User user;
  User student;
  User invalidUser;
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
    user.setPassword(user.hashPassword("admin"));
    Mockito.when(userDAO.getOneByEmail("admin@vinci.be")).thenReturn(user);
    Mockito.when(userDAO.getOneById(1)).thenReturn(user);

    student = (User) factory.getUser();
    student.setPassword(student.hashPassword("test"));
    student.setEmail("test.test@student.vinci.be");
    Mockito.when(userDAO.getOneByEmail("test.test@student.vinci.be")).thenReturn(student);
    Mockito.when(userDAO.getOneById(2)).thenReturn(student);
    Mockito.when(userDAO.addUser(student)).thenReturn(student);

    invalidUser = (User) factory.getUser();
    invalidUser.setPassword(invalidUser.hashPassword("test"));
    invalidUser.setEmail("test.test@test.be");
    Mockito.when(userDAO.getOneByEmail("test.test@test.be")).thenReturn(invalidUser);
    Mockito.when(userDAO.getOneById(3)).thenReturn(invalidUser);
    Mockito.when(userDAO.addUser(invalidUser)).thenReturn(invalidUser);
  }

  @Test
  @DisplayName("Test for the login method of UserUCC with a correct email and password")
  void loginTest() {
    assertNotNull(userUCC.login("admin@vinci.be", "admin"));
  }

  @Test
  @DisplayName("Test for the login method of UserUCC with a wrong email and good password")
  void loginTestWrongEmail() {
    assertNull(userUCC.login("wrongEmail", "admin"));
  }

  @Test
  @DisplayName("Test for the login method of UserUCC with a good email and wrong password")
  void loginTestWrongPassword() {
    assertNull(userUCC.login("admin@vinci.be", "wrongPassword"));
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

  @Test
  @DisplayName("Test for the register method of UserUCC with a student email")
  void registerTestStudent() {
    assertNotNull(userUCC.register(student));
  }

  @Test
  @DisplayName("Test for the register method of UserUCC with an invalid email")
  void registerTestInvalidEmail() {
    assertNull(userUCC.register(invalidUser));
  }


}
