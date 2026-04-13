package io.github.alexistrejo11.pimienta.shared.exception;

import java.util.Map;
import org.springframework.http.HttpStatus;

/** Missing or invalid credentials / token (HTTP 401). */
public class AuthenticationException extends PimientaException {

  public AuthenticationException(
      String clientMessage, Map<String, Object> context, String logDetails) {
    super(
        ErrorCode.AUTHENTICATION_FAILED,
        HttpStatus.UNAUTHORIZED,
        clientMessage,
        context,
        logDetails,
        null);
  }

  public AuthenticationException(
      String clientMessage, Map<String, Object> context, String logDetails, Throwable cause) {
    super(
        ErrorCode.AUTHENTICATION_FAILED,
        HttpStatus.UNAUTHORIZED,
        clientMessage,
        context,
        logDetails,
        cause);
  }
}
