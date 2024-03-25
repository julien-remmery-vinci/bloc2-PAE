package be.vinci.pae.presentation.exceptions;


import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * Class for the ConflictException.
 */
public class ConflictException extends WebApplicationException {

  /**
   * Constructor without a message.
   */
  public ConflictException() {
    super(Response.status(Status.CONFLICT)
        .build());
  }

  /**
   * Constructor with a message.
   *
   * @param message the message
   */
  public ConflictException(String message) {
    super(Response.status(Status.CONFLICT)
        .entity(message)
        .type("text/plain")
        .build());
  }

  /**
   * Constructor with a cause.
   *
   * @param cause the cause
   */
  public ConflictException(Throwable cause) {
    super(Response.status(Status.CONFLICT)
        .entity(cause.getMessage())
        .type("text/plain")
        .build());
  }
}

