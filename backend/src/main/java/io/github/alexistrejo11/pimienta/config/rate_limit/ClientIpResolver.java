package io.github.alexistrejo11.pimienta.config.rate_limit;

import jakarta.servlet.http.HttpServletRequest;

public final class ClientIpResolver {

  private static final String X_FORWARDED_FOR = "X-Forwarded-For";

  private ClientIpResolver() {
  }

  // Resolves the client IP address from the request headers.
  // If the X-Forwarded-For header is present, it returns the first IP address.
  // If the X-Forwarded-For header is not present, it returns the remote address.
  // If the remote address is not present, it returns "unknown".
  public static String resolve(HttpServletRequest request) {
    String forwarded = request.getHeader(X_FORWARDED_FOR);
    if (forwarded != null && !forwarded.isBlank()) {
      int comma = forwarded.indexOf(',');
      String first = comma > 0 ? forwarded.substring(0, comma).trim() : forwarded.trim();
      if (!first.isEmpty()) {
        return first;
      }
    }
    String remote = request.getRemoteAddr();
    return remote != null ? remote : "unknown";
  }
}