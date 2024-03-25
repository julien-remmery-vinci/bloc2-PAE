package be.vinci.pae.presentation.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * Class for the ForbiddenException.
 */
public class ForbiddenException extends WebApplicationException {

  /**
   * Constructor without a message.
   */
  public ForbiddenException() {
    super(Response.status(Response.Status.FORBIDDEN)
        .build());
  }

  /**
   * Constructor with a message.
   *
   * @param message the message
   */
  public ForbiddenException(String message) {
    super(Response.status(Status.FORBIDDEN)
        .entity(message)
        .type("text/plain")
        .build());
  }

  /**
   * Constructor with a cause.
   *
   * @param cause the cause
   */
  public ForbiddenException(Throwable cause) {
    super(Response.status(Status.FORBIDDEN)
        .entity(cause.getMessage())
        .type("text/plain")
        .build());
  }
}
