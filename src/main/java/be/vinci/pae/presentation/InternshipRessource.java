package be.vinci.pae.presentation;

import be.vinci.pae.business.internship.InternshipDTO;
import be.vinci.pae.business.internship.InternshipUCC;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.presentation.exceptions.NotFoundException;
import be.vinci.pae.presentation.filters.Authorize;
import be.vinci.pae.presentation.filters.Log;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import java.time.LocalDate;
import org.glassfish.jersey.server.ContainerRequest;

/**
 * Internship route.
 */
@Singleton
@Path("/internships")
@Log
public class InternshipRessource {

  @Inject
  private InternshipUCC internshipUCC;

  /**
   * Get an internship by its id.
   *
   * @param request the request's context
   * @return the internship
   */
  @GET
  @Authorize
  @Produces(MediaType.APPLICATION_JSON)
  public InternshipDTO getInternshipById(@Context ContainerRequest request) {
    UserDTO user = (UserDTO) request.getProperty("user");
    if (user == null) {
      throw new NotFoundException("User not found");
    }
    return internshipUCC.getInternshipById(user).stream()
        .filter(
            internship -> internship.getSignatureDate().toLocalDate().getYear() == LocalDate.now()
                .getYear()).findFirst()
        .orElseThrow(() -> new NotFoundException("Internship not found for the current year"));

  }

}


