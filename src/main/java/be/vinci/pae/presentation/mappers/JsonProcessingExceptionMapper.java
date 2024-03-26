package be.vinci.pae.presentation.mappers;

import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * JsonProcessingExceptionMapper. Maps JsonMappingException to a 400 response. Used when
 * deserializing a JSON object with values that don't match.
 */
@Provider
public class JsonProcessingExceptionMapper implements ExceptionMapper<JsonMappingException> {

  @Override
  public Response toResponse(JsonMappingException e) {
    return Response.status(Response.Status.BAD_REQUEST)
        .entity("Error while parsing JSON: Check your inputs")
        .type("text/plain")
        .build();
  }
}
