package io.github.alexistrejo11.pimienta.config.web;

import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;

public final class TraceIds {

  private TraceIds() {}

  public static String from(HttpServletRequest request) {
    Object v = request.getAttribute(TraceIdFilter.TRACE_ID_REQUEST_ATTR);
    if (v instanceof String s && !s.isBlank()) {
      return s;
    }
    return UUID.randomUUID().toString();
  }
}
