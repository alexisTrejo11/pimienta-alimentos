package io.github.alexistrejo11.pimienta.shared.exception;

import java.util.Map;
import org.springframework.http.HttpStatus;

/** Business rule or domain validation failure (HTTP 400), distinct from Jakarta bean validation. */
public class BusinessValidationException extends PimientaException {

  public BusinessValidationException(
      String clientMessage, Map<String, Object> context, String logDetails) {
    super(ErrorCode.VALIDATION_FAILED, HttpStatus.BAD_REQUEST, clientMessage, context, logDetails, null);
  }
}
