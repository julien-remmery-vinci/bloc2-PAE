package be.vinci.pae.presentation;

import be.vinci.pae.business.internship.InternshipDTO;
import be.vinci.pae.business.internship.InternshipUCC;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.presentation.exceptions.BadRequestException;
import be.vinci.pae.presentation.exceptions.NotFoundException;
import be.vinci.pae.presentation.filters.Authorize;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.jersey.server.ContainerRequest;

/**
 * Internship route.
 */
@Singleton
@Path("/internships")
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
    return internshipUCC.getInternshipById(user);
  }

  /**
   * Add an internship.
   *
   * @param request    the request's context
   * @param internship the internship to add
   * @return the added internship
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public InternshipDTO addInternship(@Context ContainerRequest request, InternshipDTO internship) {
    if (internship.getIdCompany() < 0) {
      throw new BadRequestException("Invalid Company id");
    }
    if (internship.getIdInternshipSupervisor() < 0) {
      throw new BadRequestException("Invalid Internship Supervisor id");
    }
    if (internship.getIdContact() < 0) {
      throw new BadRequestException("Invalid Contact id");
    }
    if (internship.getIdStudent() < 0) {
      throw new BadRequestException("Invalid Student id");
    }
    internship.setIdStudent(((UserDTO) request.getProperty("user")).getIdUser());
    return internshipUCC.addInternship(internship);
  }
}


