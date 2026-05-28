package io.github.alexistrejo11.pimienta.module.notification.infrastructure.adapter.inbound.web.dto;

import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationStatus;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationType;
import io.github.alexistrejo11.pimienta.shared.web.PageableRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Restricted search for managers: server forces LOG channel and {@code createdAt} on the current
 * calendar day; optional filters below narrow results within that window.
 */
@Schema(
    description =
        "Filters for manager notification log search (GET /api/v1/notifications/logs). "
            + "Only LOG channel entries from today are returned.")
public class NotificationManagerLogSearchRequest extends PageableRequest {

  @Schema(description = "Filter by notification id.")
  private UUID id;

  @Schema(description = "Filter by type.", example = "ACCOUNT_PENDING_APPROVAL")
  private NotificationType type;

  @Schema(description = "Filter by status.", example = "SENT")
  private NotificationStatus status;

  @Schema(description = "Filter by recipient user id.", example = "1")
  private Long recipientUserId;

  @Schema(description = "Filter by related user id.", example = "42")
  private Long relatedUserId;

  @Schema(description = "Correlation id contains (case-insensitive).", example = "account-pending")
  private String correlationId;

  @Schema(description = "Subject contains (case-insensitive).", example = "approval")
  private String subjectContains;

  @Schema(description = "Body contains (case-insensitive).", example = "registered")
  private String bodyContains;

  @Schema(description = "Sent at from (inclusive), within today.", example = "2026-05-28T08:00:00")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime sentFrom;

  @Schema(description = "Sent at to (inclusive), within today.", example = "2026-05-28T18:00:00")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime sentTo;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public NotificationType getType() {
    return type;
  }

  public void setType(NotificationType type) {
    this.type = type;
  }

  public NotificationStatus getStatus() {
    return status;
  }

  public void setStatus(NotificationStatus status) {
    this.status = status;
  }

  public Long getRecipientUserId() {
    return recipientUserId;
  }

  public void setRecipientUserId(Long recipientUserId) {
    this.recipientUserId = recipientUserId;
  }

  public Long getRelatedUserId() {
    return relatedUserId;
  }

  public void setRelatedUserId(Long relatedUserId) {
    this.relatedUserId = relatedUserId;
  }

  public String getCorrelationId() {
    return correlationId;
  }

  public void setCorrelationId(String correlationId) {
    this.correlationId = correlationId;
  }

  public String getSubjectContains() {
    return subjectContains;
  }

  public void setSubjectContains(String subjectContains) {
    this.subjectContains = subjectContains;
  }

  public String getBodyContains() {
    return bodyContains;
  }

  public void setBodyContains(String bodyContains) {
    this.bodyContains = bodyContains;
  }

  public LocalDateTime getSentFrom() {
    return sentFrom;
  }

  public void setSentFrom(LocalDateTime sentFrom) {
    this.sentFrom = sentFrom;
  }

  public LocalDateTime getSentTo() {
    return sentTo;
  }

  public void setSentTo(LocalDateTime sentTo) {
    this.sentTo = sentTo;
  }
}
