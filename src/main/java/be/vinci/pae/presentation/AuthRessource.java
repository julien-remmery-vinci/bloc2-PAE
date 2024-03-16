package be.vinci.pae.presentation;

import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.user.UserUCC;
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
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.Date;
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
    // verify if email is valid
    if (!json.get("email").asText()
        .matches("^[a-zA-Z0-9._%+-]+\\.[a-zA-Z0-9._%+-]+@(vinci\\.be|student\\.vinci\\.be)$")) {
      throw new WebApplicationException("email is not valid", Response.Status.BAD_REQUEST);
    }
    // verify if email or password are empty
    if (json.get("email").asText().isEmpty() || json.get("password").asText().isEmpty()) {
      throw new WebApplicationException("email or password is empty", Response.Status.BAD_REQUEST);
    }
    String email = json.get("email").asText();
    String password = json.get("password").asText();

    // Try to log in
    UserDTO user = userUCC.login(email, password);

    // Handle user not logged
    if (user == null) {
      throw new WebApplicationException("wrong email or password", Status.UNAUTHORIZED);
    }

    String token = generateToken(user);
    if (token == null) {
      return null;
    }
    ObjectNode result = jsonMapper.createObjectNode();
    result.put("token", token);
    result.put("user", jsonMapper.convertValue(user, ObjectNode.class));
    // Return token and user
    return result;
  }

  /**
   * Create a UserDTO.
   *
   * @param user the user to register
   * @return a token and the registered user
   */
  @POST
  @Path("register")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode register(UserDTO user) {
    // Get and check credentials
    if (user.getFirstname() == null || user.getLastname() == null || user.getPassword() == null
        || user.getEmail() == null
        || user.getPhoneNumber() == null) {
      throw new WebApplicationException("parameters required", Response.Status.BAD_REQUEST);
    }
    if (!user.getEmail()
            .matches("^[a-zA-Z0-9._%+-]+\\.[a-zA-Z0-9._%+-]+@(vinci\\.be|student\\.vinci\\.be)$")) {
      throw new WebApplicationException("email is not valid", Response.Status.BAD_REQUEST);
    }
    if (user.getRole() != null && !user.getRole().toString().equals("A")
            && !user.getRole().toString().equals("P")) {
      throw new WebApplicationException("role is not valid", Response.Status.BAD_REQUEST);
    }
    if (user.getFirstname().isEmpty() || user.getLastname().isEmpty() || user.getPhoneNumber()
        .isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
      throw new WebApplicationException("parameters empty", Response.Status.BAD_REQUEST);
    }

    user = userUCC.register(user);

    if (user == null) {
      throw new WebApplicationException("User already exists", Status.CONFLICT);
    }
    String token = generateToken(user);
    if (token == null) {
      return null;
    }
    ObjectNode result = jsonMapper.createObjectNode();
    result.put("token", token);
    result.put("user", jsonMapper.convertValue(user, ObjectNode.class));
    // Return token and user
    return result;
  }

  /**
   * Create token expiring in 1 hour (3600000ms).
   *
   * @param user the user to generate token for
   * @return the generated token
   */
  private String generateToken(UserDTO user) {
    String token;
    try {
      token = JWT.create()
          .withIssuer("auth0")
          .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
          .withClaim("user", user.getIdUser()).sign(this.jwtAlgorithm);
    } catch (Exception e) {
      System.out.println("Unable to create token");
      return null;
    }
    return token;
  }

  /**
   * Get the user token.
   *
   * @param request the request
   * @return the user
   */
  @GET
  @Path("/user")
  @Authorize
  @Produces(MediaType.APPLICATION_JSON)
  public UserDTO getUser(@Context ContainerRequest request) {
    UserDTO authenticatedUser = (UserDTO) request.getProperty("user");
    if (authenticatedUser.getIdUser() <= 0) {
      throw new WebApplicationException("User not found", Status.NOT_FOUND);
    }
    // Return the user
    return authenticatedUser;
  }
}