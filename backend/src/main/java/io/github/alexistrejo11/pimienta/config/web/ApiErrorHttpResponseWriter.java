package io.github.alexistrejo11.pimienta.config.web;

import io.github.alexistrejo11.pimienta.shared.exception.ErrorCode;
import io.github.alexistrejo11.pimienta.shared.exception.api.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import tools.jackson.databind.ObjectMapper;

public final class ApiErrorHttpResponseWriter {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  private ApiErrorHttpResponseWriter() {}

  public static void write(
      HttpServletRequest request,
      HttpServletResponse response,
      HttpStatus status,
      ErrorCode errorCode,
      String message)
      throws IOException {
    String traceId = TraceIds.from(request);
    response.setStatus(status.value());
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setHeader(TraceIdFilter.TRACE_ID_HEADER, traceId);
    ApiErrorResponse body = ApiErrorResponse.of(errorCode, message, traceId, null);
    response.getOutputStream().write(MAPPER.writeValueAsBytes(body));
  }
}
