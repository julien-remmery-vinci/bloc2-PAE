package be.vinci.pae.presentation;


import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.contact.ContactUCC;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.presentation.filters.Authorize;
import be.vinci.pae.presentation.filters.Log;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;
import java.util.List;
import org.glassfish.jersey.server.ContainerRequest;

/**
 * addContact route.
 */
@Singleton
@Path("/contact")
@Log
public class ContactRessource {

  @Inject
  private ContactUCC contactUCC;

  /**
   * Get all contacts.
   *
   * @param request the request's context
   * @return the list of contacts
   */
  @GET
  @Path("/all")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<ContactDTO> getContacts(@Context ContainerRequest request) {
    UserDTO user = (UserDTO) request.getProperty("user");
    if (user == null) {
      throw new WebApplicationException("User not found", Status.NOT_FOUND);
    }
    return contactUCC.getContacts(user);
  }

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

  /**
   * Meet a contact.
   *
   * @param request   the request's context
   * @param idContact the id of the contact
   * @param json      json containing the meet place
   * @return the contact
   */
  @POST
  @Path("/{id}/meet")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public ContactDTO meetContact(@Context ContainerRequest request, @PathParam("id") int idContact,
      JsonNode json) {
    if (idContact < 0) {
      throw new WebApplicationException("Invalid id", Status.BAD_REQUEST);
    }
    String meetPlace = json.get("meetPlace").asText();
    if (!json.hasNonNull("meetPlace") || meetPlace.isBlank()) {
      throw new WebApplicationException("Meet place is required", Status.BAD_REQUEST);
    }
    ContactDTO contact = contactUCC.meetContact(idContact, meetPlace,
        ((UserDTO) request.getProperty("user")).getIdUser());
    if (contact == null) {
      throw new WebApplicationException("Contact not found", Status.NOT_FOUND);
    }
    return contact;
  }

  /**
   * Unfollow a contact.
   *
   * @param request   the request's context
   * @param idContact the id of the contact
   * @return the contact
   */
  @POST
  @Path("/{id}/unfollow")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public ContactDTO unfollowContact(@Context ContainerRequest request,
      @PathParam("id") int idContact) {
    if (idContact < 0) {
      throw new WebApplicationException("Invalid id", Status.BAD_REQUEST);
    }
    ContactDTO contact = contactUCC.unfollowContact(idContact,
        ((UserDTO) request.getProperty("user")).getIdUser());
    if (contact == null) {
      throw new WebApplicationException("Contact not found", Status.NOT_FOUND);
    }
    return contact;
  }

  /**
   * Add a contact.
   *
   * @param request the request's context
   * @param contact the contact to add
   * @return the contact
   */
  @POST
  @Path("/add")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public ContactDTO addContact(@Context ContainerRequest request, ContactDTO contact) {
    if (contact.getIdCompany() < 0) {
      throw new WebApplicationException("Invalid id", Status.BAD_REQUEST);
    }
    contact.setIdStudent(((UserDTO) request.getProperty("user")).getIdUser());
    return contactUCC.addContact(contact);
  }

}
