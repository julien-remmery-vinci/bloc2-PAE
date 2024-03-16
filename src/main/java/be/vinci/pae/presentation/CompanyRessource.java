package be.vinci.pae.presentation;

import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.company.CompanyUCC;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

/**
 * Company route.
 */
@Singleton
@Path("/company")
public class CompanyRessource {

  @Inject
  private CompanyUCC companyUCC;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<CompanyDTO> getAll() {
    return companyUCC.getAll();
  }

}
