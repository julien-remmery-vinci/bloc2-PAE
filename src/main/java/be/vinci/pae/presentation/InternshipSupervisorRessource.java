package be.vinci.pae.presentation;

import be.vinci.pae.business.internshipsupervisor.InternshipSupervisorDTO;
import be.vinci.pae.business.internshipsupervisor.InternshipSupervisorUCC;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

/**
 * InternshipSupervisor route.
 */

@Singleton
@Path("/internshipSupervisors")
public class InternshipSupervisorRessource {

  @Inject
  private InternshipSupervisorUCC internshipSupervisorUCC;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<InternshipSupervisorDTO> getAll() {
    return internshipSupervisorUCC.getAll();
  }

}