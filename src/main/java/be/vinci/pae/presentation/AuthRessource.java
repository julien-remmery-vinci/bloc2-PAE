package be.vinci.pae.presentation;

import be.vinci.pae.business.UserDTO;
import be.vinci.pae.business.UserUCC;
import be.vinci.pae.presentation.filters.Authorize;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.glassfish.jersey.server.ContainerRequest;

/**
 * Login and register routes.
 */
@Singleton
@Path("/auths")
public class AuthRessource {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();
  @Inject
  private UserUCC userUCC;

  /**
   * Get the email and password. Check if email and password are not null.
   *
   * @param json contains the email and password
   * @return a token and the logged user
   */
  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode login(JsonNode json) {
    // Get and check credentials
    if (!json.hasNonNull("email") || !json.hasNonNull("password")) {
      throw new WebApplicationException("email or password required", Response.Status.BAD_REQUEST);
    }
    String email = json.get("email").asText();
    String password = json.get("password").asText();

    // Try to log in
    UserDTO user = userUCC.login(email, password);

    // Handle user not logged
    if (user == null) {
      throw new WebApplicationException("wrong email or password", Status.UNAUTHORIZED);
    }

    // Create token
    String token;
    try {
      token = JWT.create().withIssuer("auth0")
          .withClaim("user", user.getIdUser()).sign(this.jwtAlgorithm);
    } catch (Exception e) {
      System.out.println("Unable to create token");
      return null;
    }

    // Return token, firstname and lastname
    return jsonMapper.createObjectNode()
        .put("token", token)
        .put("firstname", user.getFirstname())
        .put("lastname", user.getLastname());
  }

  /**
   * Get the user token.
   *
   * @param request the request
   * @return the user
   */
  @POST
  @Path("/user")
  @Authorize
  @Produces(MediaType.APPLICATION_JSON)
  public UserDTO userToken(@Context ContainerRequest request) {
    UserDTO authenticatedUser = (UserDTO) request.getProperty("user");
    if (authenticatedUser.getIdUser() <= 0) {
      throw new WebApplicationException("User not found", Status.NOT_FOUND);
    }
    return userUCC.getUser(authenticatedUser.getIdUser());
  }
}
