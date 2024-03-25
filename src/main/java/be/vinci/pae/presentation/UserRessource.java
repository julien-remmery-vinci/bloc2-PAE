/**
 * UserRessource class. This class is responsible for handling HTTP requests related to users. It
 * uses the UserUCC class to perform business logic operations and returns the results as JSON.
 */

package be.vinci.pae.presentation;

import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.user.UserUCC;
import be.vinci.pae.presentation.exceptions.UnauthorizedException;
import be.vinci.pae.presentation.filters.Authorize;
import be.vinci.pae.presentation.filters.Log;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.List;
import org.glassfish.jersey.server.ContainerRequest;

/**
 * This class is a Singleton and a RESTful resource that handles HTTP requests related to users.
 */
@Singleton
@Path("/users")
@Log
public class UserRessource {

  /**
   * The UserUCC instance used to perform business logic operations related to users.
   */
  @Inject
  private UserUCC userUCC;

  /**
   * Handles HTTP POST requests to register a new user. This method uses the injected UserUCC
   * instance to register a new user and returns the registered user as a UserDTO object. The
   * UserDTO object is automatically converted to JSON by the JAX-RS runtime.
   *
   * @param json the JSON object containing the user's information
   * @return the registered user as a UserDTO object
   */
  @POST
  @Path("/changepassword")
  @Authorize
  @Consumes(MediaType.APPLICATION_JSON)
  public Response changePassword(JsonNode json, @Context ContainerRequest request) {
    UserDTO authenticatedUser = (UserDTO) request.getProperty("user");
    String oldPassword = json.get("oldPassword").asText();
    String newPassword = json.get("newPassword").asText();
    String confirmationPassword = json.get("confirmationPassword").asText();

    if (authenticatedUser.getIdUser() <= 0) {
      throw new WebApplicationException("User not found", Status.NOT_FOUND);
    }

    if (oldPassword == null || newPassword == null || confirmationPassword == null) {
      throw new WebApplicationException("oldPassword or newPassword or confimation required",
          Response.Status.BAD_REQUEST);
    }

    if (oldPassword.equals(newPassword)) {
      throw new WebApplicationException("New password is the same as the old one",
          Response.Status.BAD_REQUEST);
    }

    if (!newPassword.equals(confirmationPassword) || newPassword.isEmpty()) {
      throw new WebApplicationException("New password and confirmation are not the same or empty",
          Response.Status.BAD_REQUEST);
    }

    if (oldPassword.isEmpty()) {
      throw new WebApplicationException(
          "oldPassword or newPassword or confirmationPassword is empty",
          Response.Status.BAD_REQUEST);
    }

    if (userUCC.updateUser(authenticatedUser, oldPassword, newPassword) == null) {
      throw new UnauthorizedException("oldPassword is wrong");
    }
    return Response.status(204, "Password changed").build();
  }

  /**
   * Handles HTTP GET requests to fetch all users. This method uses the injected UserUCC instance to
   * fetch all users and returns them as a list of UserDTO objects. The list is automatically
   * converted to JSON by the JAX-RS runtime.
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