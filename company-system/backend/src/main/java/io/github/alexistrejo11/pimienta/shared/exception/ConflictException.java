package io.github.alexistrejo11.pimienta.shared.exception;

import java.util.Map;
import org.springframework.http.HttpStatus;

public class ConflictException extends PimientaException {

  public ConflictException(
      ErrorCode errorCode, String clientMessage, Map<String, Object> context, String logDetails) {
    super(errorCode, HttpStatus.CONFLICT, clientMessage, context, logDetails, null);
  }
}
