package be.vinci.pae.presentation.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
/**
 * Class for the PreconditionFailedException.
 */
public class PreconditionFailedException extends WebApplicationException {

  /**
   * Constructor without a message.
   */
  public PreconditionFailedException() {
    super(Response.status(Status.PRECONDITION_FAILED)
        .build());
  }

  /**
   * Constructor with a message.
   *
   * @param message the message
   */
  public PreconditionFailedException(String message) {
    super(Response.status(Status.PRECONDITION_FAILED)
        .entity(message)
        .type("text/plain")
        .build());
  }

  /**
   * Constructor with a cause.
   *
   * @param cause the cause
   */
  public PreconditionFailedException(Throwable cause) {
    super(Response.status(Status.PRECONDITION_FAILED)
        .entity(cause.getMessage())
        .type("text/plain")
        .build());
  }
}
