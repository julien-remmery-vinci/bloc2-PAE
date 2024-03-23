package be.vinci.pae.presentation.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class NotFoundException extends WebApplicationException {

  /**
   * Constructor without a message.
   */
  public NotFoundException() {
    super(Response.status(Status.NOT_FOUND)
        .build());
  }

  /**
   * Constructor with a message.
   *
   * @param message the message
   */
  public NotFoundException(String message) {
    super(Response.status(Status.NOT_FOUND)
        .entity(message)
        .type("text/plain")
        .build());
  }

  /**
   * Constructor with a cause.
   *
   * @param cause the cause
   */
  public NotFoundException(Throwable cause) {
    super(Response.status(Status.NOT_FOUND)
        .entity(cause.getMessage())
        .type("text/plain")
        .build());
  }
}
