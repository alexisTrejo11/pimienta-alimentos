package io.github.alexistrejo11.pimienta.shared.ratelimit;

/**
 * Named rate-limit presets; numeric limits are configured in
 * {@code pimienta.rate-limiting.profiles.*}.
 */
public enum RateLimitProfile {

  /** Default API traffic (single-resource reads and routine writes). */
  STANDARD,

  /**
   * List/search, statistics, and other read-heavy GETs (higher sustained throughput
   * per IP than {@link #STANDARD}).
   */
  READ_HEAVY,

  /** Credential and account-enumeration sensitive (login, registration). */
  STRICT,

  /**
   * Session lifecycle (refresh, logout): stricter than {@link #STANDARD}, looser than
   * {@link #STRICT} so legitimate clients are not starved.
   */
  AUTH_SESSION,

  /** Destructive or high-impact mutations (deletes, bans, approvals, terminal pipeline steps). */
  SENSITIVE_OPERATIONS
}