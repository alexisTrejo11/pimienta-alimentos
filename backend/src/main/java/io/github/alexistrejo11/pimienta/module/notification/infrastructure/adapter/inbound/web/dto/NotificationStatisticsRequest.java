package io.github.alexistrejo11.pimienta.module.notification.infrastructure.adapter.inbound.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

@Schema(description = "Time interval for notification statistics (GET .../management/statistics).")
public class NotificationStatisticsRequest {

  @NotNull(message = "from is required")
  @Schema(
      description = "Interval start (inclusive), ISO-8601 date-time.",
      example = "2026-05-01T00:00:00",
      requiredMode = Schema.RequiredMode.REQUIRED,
      type = "string",
      format = "date-time")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime from;

  @NotNull(message = "to is required")
  @Schema(
      description = "Interval end (inclusive), ISO-8601 date-time.",
      example = "2026-05-28T23:59:59",
      requiredMode = Schema.RequiredMode.REQUIRED,
      type = "string",
      format = "date-time")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime to;

  public LocalDateTime getFrom() {
    return from;
  }

  public void setFrom(LocalDateTime from) {
    this.from = from;
  }

  public LocalDateTime getTo() {
    return to;
  }

  public void setTo(LocalDateTime to) {
    this.to = to;
  }

  @AssertTrue(message = "from must be before or equal to to")
  @Schema(hidden = true)
  public boolean isIntervalOrdered() {
    return from == null || to == null || !from.isAfter(to);
  }
}
