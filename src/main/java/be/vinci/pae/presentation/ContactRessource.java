package be.vinci.pae.presentation;


import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.contact.ContactUCC;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.presentation.exceptions.BadRequestException;
import be.vinci.pae.presentation.exceptions.NotFoundException;
import be.vinci.pae.presentation.filters.Authorize;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import org.glassfish.jersey.server.ContainerRequest;

/**
 * addContact route.
 */
@Singleton
@Path("/contacts")
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
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<ContactDTO> getContacts(@Context ContainerRequest request) {
    UserDTO user = (UserDTO) request.getProperty("user");
    if (user == null) {
      throw new NotFoundException("User not found");
    }
    return contactUCC.getContacts(user);
  }

  /**
   * Refuse a contact.
   *
   * @param request       the request's context
   * @param idContact     the id of the contact
   * @param refusalReason string containing the refusal reason
   * @return the contact
   */
  @POST
  @Path("/{id}/refuse")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public ContactDTO refuseContact(@Context ContainerRequest request, @PathParam("id") int idContact,
      String refusalReason) {
    if (idContact < 0) {
      throw new BadRequestException("Invalid id");
    }
    if (refusalReason == null || refusalReason.isBlank()) {
      throw new BadRequestException("Refusal reason is required");
    }
    ContactDTO contact = contactUCC.refuseContact(idContact, refusalReason,
        ((UserDTO) request.getProperty("user")).getIdUser());
    if (contact == null) {
      throw new NotFoundException("Contact not found");
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
    System.out.println(json.asText());
    if (idContact < 0) {
      throw new BadRequestException("Invalid id");
    }
    JsonNode meetPlaceNode = json.get("meetPlace");
    if (meetPlaceNode == null) {
      throw new BadRequestException("Meet place is required");
    }
    String meetPlace = meetPlaceNode.asText();
    if (meetPlace.isBlank()) {
      throw new BadRequestException("Meet place is required");
    }
    ContactDTO contact = contactUCC.meetContact(idContact, meetPlace,
        ((UserDTO) request.getProperty("user")).getIdUser());
    if (contact == null) {
      throw new NotFoundException("Contact not found");
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
      throw new BadRequestException("Invalid id");
    }
    ContactDTO contact = contactUCC.unfollowContact(idContact,
        ((UserDTO) request.getProperty("user")).getIdUser());
    if (contact == null) {
      throw new NotFoundException("Contact not found");
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
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public ContactDTO addContact(@Context ContainerRequest request, ContactDTO contact) {
    if (contact.getIdCompany() < 0) {
      throw new BadRequestException("Invalid id");
    }
    contact.setIdStudent(((UserDTO) request.getProperty("user")).getIdUser());
    contact.setUser((UserDTO) request.getProperty("user"));
    return contactUCC.addContact(contact);
  }

}
