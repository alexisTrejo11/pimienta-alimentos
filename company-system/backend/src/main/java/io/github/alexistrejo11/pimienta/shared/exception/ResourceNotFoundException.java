package io.github.alexistrejo11.pimienta.shared.exception;

import java.util.Map;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends PimientaException {

  public ResourceNotFoundException(
      ErrorCode errorCode, String clientMessage, Map<String, Object> context, String logDetails) {
    super(errorCode, HttpStatus.NOT_FOUND, clientMessage, context, logDetails, null);
  }
}
