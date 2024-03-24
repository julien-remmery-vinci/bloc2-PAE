package be.vinci.pae.presentation;

import be.vinci.pae.business.internshipsupervisor.InternshipSupervisorUCC;
import be.vinci.pae.presentation.filters.Log;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

/**
 * InternshipSupervisor route.
 */
@Singleton
@Path("/internshipsupervisor")
@Log
public class InternshipSupervisorRessource {

  @Inject
  private InternshipSupervisorUCC internshipSupervisorUCC;

  /**
   * Get an internship supervisor by its id.
   *
   * @param id the id
   */
  @GET
  public void getInternshipSupervisorById(int id) {
    internshipSupervisorUCC.getInternshipSupervisorById(id);
  }

}
