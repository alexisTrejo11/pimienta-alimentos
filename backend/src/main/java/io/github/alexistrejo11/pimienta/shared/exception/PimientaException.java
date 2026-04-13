package io.github.alexistrejo11.pimienta.shared.exception;

import java.util.Collections;
import java.util.Map;
import org.springframework.http.HttpStatus;

/**
 * Base application exception. {@code getMessage()} is safe for API clients. {@link #logDetails()} is
 * for logging only and must never be returned in HTTP responses.
 */
public class PimientaException extends RuntimeException {

  private final ErrorCode errorCode;
  private final HttpStatus httpStatus;
  private final Map<String, Object> context;
  private final String logDetails;

  protected PimientaException(
      ErrorCode errorCode,
      HttpStatus httpStatus,
      String clientMessage,
      Map<String, Object> context,
      String logDetails,
      Throwable cause) {
    super(clientMessage, cause);
    this.errorCode = errorCode;
    this.httpStatus = httpStatus;
    this.context = context != null ? Map.copyOf(context) : Collections.emptyMap();
    this.logDetails = logDetails;
  }

  public ErrorCode errorCode() {
    return errorCode;
  }

  public HttpStatus httpStatus() {
    return httpStatus;
  }

  public Map<String, Object> context() {
    return context;
  }

  /** Details for server logs only; may contain sensitive or internal data. */
  public String logDetails() {
    return logDetails;
  }
}
