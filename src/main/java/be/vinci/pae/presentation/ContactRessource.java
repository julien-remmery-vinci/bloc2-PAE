package be.vinci.pae.presentation;


import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.contact.ContactUCC;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;

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
   * @param id the id of the contact
   * @return the contact
   */
  @Path("/{id}/accept")
  public ContactDTO refuseContact(@PathParam("id") int id, String refusalReason) {
    if (id < 0) {
      throw new WebApplicationException("Invalid id", Status.BAD_REQUEST);
    }
    if (refusalReason == null || refusalReason.isEmpty()) {
      throw new WebApplicationException("Refusal reason is required", Status.BAD_REQUEST);
    }
    ContactDTO contact = contactUCC.refuseContact(id, refusalReason);
    if (contact == null) {
      throw new WebApplicationException("Contact not found", Status.NOT_FOUND);
    }
    return contact;
  }

}
