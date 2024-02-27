package be.vinci.pae.presentation.filters;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * The CORSFilter class is a filter used to handle Cross-Origin Resource Sharing (CORS) headers for
 * HTTP requests and responses.
 *
 * <p>It adds necessary CORS headers to the response to allow cross-origin requests from a
 * specific origin.
 */
@Provider
public class CorsFilter implements ContainerResponseFilter {

  /**
   * Filters the container response context to add CORS headers.
   *
   * @param requestContext  the context of the container request
   * @param responseContext the context of the container response
   * @throws IOException if an I/O error occurs while processing the response
   */
  @Override
  public void filter(ContainerRequestContext requestContext,
      ContainerResponseContext responseContext) throws IOException {
    responseContext.getHeaders().add("Access-Control-Allow-Origin", "http://localhost:3000");
    responseContext.getHeaders()
        .add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
    responseContext.getHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
    responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
  }
}