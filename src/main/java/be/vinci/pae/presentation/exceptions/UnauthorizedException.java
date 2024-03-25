package be.vinci.pae.presentation.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * Class for the UnauthorizedException.
 */
public class UnauthorizedException extends WebApplicationException {

  public UnauthorizedException() {
    super(Response.status(Status.UNAUTHORIZED)
        .build());
  }

  /**
   * Constructor with a message.
   *
   * @param message the message
   */
  public UnauthorizedException(String message) {
    super(Response.status(Status.UNAUTHORIZED)
        .entity(message)
        .type("text/plain")
        .build());
  }

  /**
   * Constructor with a cause.
   *
   * @param cause the cause
   */
  public UnauthorizedException(Throwable cause) {
    super(Response.status(Status.UNAUTHORIZED)
        .entity(cause.getMessage())
        .type("text/plain")
        .build());
  }
}
