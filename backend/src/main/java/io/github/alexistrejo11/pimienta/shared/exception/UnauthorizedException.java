package io.github.alexistrejo11.pimienta.shared.exception;

import java.util.Map;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends PimientaException {

  public UnauthorizedException(
      String clientMessage, Map<String, Object> context, String logDetails) {
    super(ErrorCode.UNAUTHORIZED, HttpStatus.UNAUTHORIZED, clientMessage, context, logDetails, null);
  }
}
