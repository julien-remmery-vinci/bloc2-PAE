package be.vinci.pae.presentation;

import be.vinci.pae.business.internship.InternshipDTO;
import be.vinci.pae.business.internship.InternshipUCC;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.user.UserDTO.Role;
import be.vinci.pae.exceptions.BadRequestException;
import be.vinci.pae.exceptions.NotFoundException;
import be.vinci.pae.presentation.filters.Authorize;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import java.sql.Date;
import java.time.LocalDate;
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
  @Authorize(roles = {Role.ADMIN, Role.STUDENT, Role.TEACHER})
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
  @Authorize(roles = {Role.STUDENT})
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
    System.out.println(internship.getSignatureDate());
    if (internship.getSignatureDate() == null) {
      throw new BadRequestException("Invalid Start Date");
    }
    Date date = Date.valueOf(LocalDate.now());
    Date startAcademicYear;
    if (date.toLocalDate().getMonthValue() >= 9) {
      startAcademicYear = Date.valueOf(LocalDate.of(LocalDate.now().getYear(), 9, 1));
    } else {
      startAcademicYear = Date.valueOf(LocalDate.of(LocalDate.now().getYear() - 1, 9, 1));
    }
    if (internship.getSignatureDate().after(date) || internship.getSignatureDate()
        .before(startAcademicYear)) {
      throw new BadRequestException("Invalid Start Date");
    }
    internship.setIdStudent(((UserDTO) request.getProperty("user")).getIdUser());
    return internshipUCC.addInternship(internship);
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{id}")
  @Authorize(roles = {Role.STUDENT})
  public InternshipDTO updateInternshipSubject(JsonNode json, @Context ContainerRequest request) {
    InternshipDTO internship = internshipUCC.getInternshipById((UserDTO) request.getProperty("user"));
    if (internship == null) {
      throw new NotFoundException("Internship not found");
    }

    String subject = json.get("subject").asText();

    if (subject == null || subject.isEmpty()) {
      throw new BadRequestException("Empty Subject");
    }
    return internshipUCC.updateInternshipSubject(internship, subject);
  }
}


