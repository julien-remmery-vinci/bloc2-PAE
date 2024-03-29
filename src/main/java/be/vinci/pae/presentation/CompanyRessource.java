package be.vinci.pae.presentation;

import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.company.CompanyUCC;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

/**
 * Company route.
 */
@Singleton
@Path("/companies")
public class CompanyRessource {

  private final ObjectMapper jsonMapper = new ObjectMapper();
  @Inject
  private CompanyUCC companyUCC;

  /**
   * Get all companies.
   *
   * @return the list of all companies
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<CompanyDTO> getAll() {
    return companyUCC.getAll();
  }

  @POST
  @Path("/{id}/blacklist")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  // TODO : add Authorize filter for teachers
  public ObjectNode blacklistCompany(@PathParam("id") int id,
      JsonNode json) {
    if (id <= 0) {
      throw new BadRequestException("L'id de l'entreprise doit être positif");
    }
    if (json.get("reason") == null || json.get("reason").asText().isEmpty()) {
      throw new BadRequestException("La raison de blacklist doit être spécifiée");
    }
    List<Object> list = companyUCC.blacklistCompany(id, json.get("reason").asText());
    ObjectNode result = jsonMapper.createObjectNode();
    result.put("company", jsonMapper.convertValue(list.get(0), ObjectNode.class));
    list.remove(0);
    ArrayNode o = jsonMapper.createArrayNode();
    for (Object object : list) {
      o.add(jsonMapper.convertValue(object, ObjectNode.class));
    }
    result.put("contacts", o);
    return result;
  }

}
