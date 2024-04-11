package be.vinci.pae.presentation.mappers;

import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * JsonProcessingExceptionMapper. Maps JsonMappingException to a 400 response. Used when
 * deserializing a JSON object with values that don't match.
 */
@Provider
public class JsonProcessingExceptionMapper implements ExceptionMapper<JsonMappingException> {

  private final Logger logger = LogManager.getLogger("WebExceptionMapper");

  @Override
  public Response toResponse(JsonMappingException e) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    e.printStackTrace(pw);
    logger.error(sw.toString());
    return Response.status(Response.Status.BAD_REQUEST)
        .entity("Error while parsing JSON: Check your inputs")
        .type("text/plain")
        .build();
  }
}
