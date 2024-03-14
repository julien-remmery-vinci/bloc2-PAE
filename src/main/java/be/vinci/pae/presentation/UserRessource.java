/**
 * UserRessource class.
 * <p>
 * This class is responsible for handling HTTP requests related to users. It uses the UserUCC class
 * to perform business logic operations and returns the results as JSON.
 */
package be.vinci.pae.presentation;

import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.user.UserUCC;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

/**
 * This class is a Singleton and a RESTful resource that handles HTTP requests related to users.
 */
@Singleton
@Path("/users")
public class UserRessource {

  /**
   * The UserUCC instance used to perform business logic operations related to users.
   */
  @Inject
  private UserUCC userUCC;

  /**
   * Handles HTTP GET requests to fetch all users.
   *
   * This method uses the injected UserUCC instance to fetch all users and returns them as a list
   * of UserDTO objects. The list is automatically converted to JSON by the JAX-RS runtime.
   *
   * @return a list of UserDTO objects representing all users
   */
  @GET
  @Path("all")
  @Produces(MediaType.APPLICATION_JSON)
  public List<UserDTO> getAllUsers() {
    return userUCC.getAllUsers();
  }

}