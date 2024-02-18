package be.vinci.pae.presentation;

import be.vinci.pae.business.UserDTO;
import be.vinci.pae.business.UserUCC;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Singleton
@Path("/auths")
public class AuthRessource {

  @Inject
  UserUCC userUCC;
  JsonMapper jsonMapper = new JsonMapper();

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

    ObjectNode data = jsonMapper.createObjectNode();
    return data;
  }

}
