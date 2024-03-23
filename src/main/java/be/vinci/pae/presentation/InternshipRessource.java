package be.vinci.pae.presentation;

import be.vinci.pae.business.internship.InternshipDTO;
import be.vinci.pae.business.internship.InternshipUCC;
import be.vinci.pae.presentation.filters.Log;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Internship route.
 */
@Singleton
@Path("/internship")
@Log
public class InternshipRessource {

  @Inject
  private InternshipUCC internshipUCC;

  /**
   * Get an internship by its id.
   *
   * @param id the id
   * @return the internship
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public InternshipDTO getInternshipById(int id) {
    return internshipUCC.getInternshipById(id);
  }

}


