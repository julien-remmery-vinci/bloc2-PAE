package be.vinci.pae.presentation.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * Class for the BadRequestException.
 */
public class BadRequestException extends WebApplicationException {

  public static final Status STATUS = Status.BAD_REQUEST;

  /**
   * Constructor without a message.
   */
  public BadRequestException() {
    super(Response.status(STATUS)
        .build());
  }

  /**
   * Constructor with a message.
   *
   * @param message the message
   */
  public BadRequestException(String message) {
    super(Response.status(STATUS)
        .entity(message)
        .type("text/plain")
        .build());
  }

  /**
   * Constructor with a cause.
   *
   * @param cause the cause
   */
  public BadRequestException(Throwable cause) {
    super(Response.status(STATUS)
        .entity(cause.getMessage())
        .type("text/plain")
        .build());
  }
}
