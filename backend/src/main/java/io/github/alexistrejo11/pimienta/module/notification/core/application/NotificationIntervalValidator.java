package io.github.alexistrejo11.pimienta.module.notification.core.application;

import io.github.alexistrejo11.pimienta.shared.exception.BusinessValidationException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

final class NotificationIntervalValidator {

  static final int MAX_STATISTICS_RANGE_DAYS = 366;

  private NotificationIntervalValidator() {}

  static void validateStatisticsInterval(LocalDateTime from, LocalDateTime to) {
    if (from == null) {
      throw new BusinessValidationException(
          "Parameter 'from' is required.",
          Map.of("field", "from"),
          "Notification statistics: missing from");
    }
    if (to == null) {
      throw new BusinessValidationException(
          "Parameter 'to' is required.",
          Map.of("field", "to"),
          "Notification statistics: missing to");
    }
    if (from.isAfter(to)) {
      throw new BusinessValidationException(
          "Parameter 'from' must be before or equal to 'to'.",
          Map.of("from", from, "to", to),
          "Notification statistics: from after to");
    }
    long days = ChronoUnit.DAYS.between(from, to);
    if (days > MAX_STATISTICS_RANGE_DAYS) {
      throw new BusinessValidationException(
          "Statistics interval must not exceed " + MAX_STATISTICS_RANGE_DAYS + " days.",
          Map.of("from", from, "to", to, "maxDays", MAX_STATISTICS_RANGE_DAYS),
          "Notification statistics: range too wide");
    }
  }
}
