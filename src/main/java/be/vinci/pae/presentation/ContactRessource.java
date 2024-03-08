package be.vinci.pae.presentation;


import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.contact.ContactUCC;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.presentation.filters.Authorize;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;
import org.glassfish.jersey.server.ContainerRequest;

/**
 * addContact route.
 */
@Singleton
@Path("/contact")
public class ContactRessource {

  @Inject
  private ContactUCC contactUCC;

  /**
   * Refuse a contact.
   *
   * @param request   the request's context
   * @param idContact the id of the contact
   * @param json      json containing the refusal reason
   * @return the contact
   */
  @POST
  @Path("/{id}/refuse")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public ContactDTO refuseContact(@Context ContainerRequest request, @PathParam("id") int idContact,
      JsonNode json) {
    if (idContact < 0) {
      throw new WebApplicationException("Invalid id", Status.BAD_REQUEST);
    }
    String refusalReason = json.get("refusalReason").asText();
    if (!json.hasNonNull("refusalReason") || refusalReason.isBlank()) {
      throw new WebApplicationException("Refusal reason is required", Status.BAD_REQUEST);
    }
    ContactDTO contact = contactUCC.refuseContact(idContact, refusalReason,
        ((UserDTO) request.getProperty("user")).getIdUser());
    if (contact == null) {
      throw new WebApplicationException("Contact not found", Status.NOT_FOUND);
    }
    return contact;
  }

}
