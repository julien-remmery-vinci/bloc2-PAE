package be.vinci.pae;

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

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the UserUCC methods.
 */
public class UserUCCTest {

  ServiceLocator locator;
  User user;
  User student;
  User teacher;
  User admin;
  User invalidRoleUser;
  User existingUser;
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
    Mockito.when(userDAO.getOneByEmail("test.test@student.vinci.be")).thenReturn(null);
    Mockito.when(userDAO.addUser(student)).thenReturn(student);

    student = (User) factory.getUser();
    student.setPassword(student.hashPassword("test"));
    student.setEmail("test.test@student.vinci.be");
    Mockito.when(userDAO.getOneByEmail("test.test@student.vinci.be")).thenReturn(null);
    Mockito.when(userDAO.addUser(student)).thenReturn(student);

    teacher = (User) factory.getUser();
    teacher.setPassword(teacher.hashPassword("test"));
    teacher.setEmail("test.test@vinci.be");
    teacher.setRole(UserDTO.Role.P);
    Mockito.when(userDAO.getOneByEmail("test.test@vinci.be")).thenReturn(null);
    Mockito.when(userDAO.addUser(teacher)).thenReturn(teacher);

    admin = (User) factory.getUser();
    admin.setPassword(admin.hashPassword("test"));
    admin.setEmail("admin.test@vinci.be");
    admin.setRole(UserDTO.Role.A);
    Mockito.when(userDAO.getOneByEmail("admin.test@vinci.be")).thenReturn(null);
    Mockito.when(userDAO.addUser(admin)).thenReturn(admin);

    invalidRoleUser = (User) factory.getUser();
    invalidRoleUser.setPassword(invalidRoleUser.hashPassword("test"));
    invalidRoleUser.setEmail("invalid.test@student.vinci.be");
    invalidRoleUser.setRole(UserDTO.Role.A);
    Mockito.when(userDAO.getOneByEmail("invalid.test@vinci.be")).thenReturn(null);
    Mockito.when(userDAO.addUser(admin)).thenReturn(admin);

    existingUser = (User) factory.getUser();
    existingUser.setEmail("test.t@student.vinci.be");
    Mockito.when(userDAO.getOneByEmail("test.t@student.vinci.be")).thenReturn(existingUser);
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
    assertEquals(UserDTO.Role.E, student.getRole());
  }

  @Test
  @DisplayName("Test for the register method of UserUCC with a teacher email")
  void registerTestTeacher() {
    assertNotNull(userUCC.register(teacher));
    assertEquals(UserDTO.Role.P, teacher.getRole());
  }

  @Test
  @DisplayName("Test for the register method of UserUCC with an admin email")
  void registerTestAdmin() {
    assertNotNull(userUCC.register(admin));
    assertEquals(UserDTO.Role.A, admin.getRole());
  }

  @Test
  @DisplayName("Test for the register method of UserUCC with an existing email")
  void registerTestInvalidEmail() {
    assertNull(userUCC.register(existingUser));
  }


}
