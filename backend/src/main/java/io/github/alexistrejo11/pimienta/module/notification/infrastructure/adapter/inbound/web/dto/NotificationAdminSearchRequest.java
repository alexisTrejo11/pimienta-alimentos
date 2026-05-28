package io.github.alexistrejo11.pimienta.module.notification.infrastructure.adapter.inbound.web.dto;

import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationChannel;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationStatus;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationType;
import io.github.alexistrejo11.pimienta.shared.web.PageableRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;

@Schema(description = "Filters and pagination for admin notification search (GET /api/v1/notifications/management).")
public class NotificationAdminSearchRequest extends PageableRequest {

  @Schema(description = "Filter by notification id.")
  private UUID id;

  @Schema(description = "Filter by channel.", example = "EMAIL")
  private NotificationChannel channel;

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

  @Schema(description = "Recipient email contains (case-insensitive).", example = "admin@")
  private String recipientEmailContains;

  @Schema(description = "Recipient phone contains.", example = "300")
  private String recipientPhoneContains;

  @Schema(description = "Subject contains (case-insensitive).", example = "approval")
  private String subjectContains;

  @Schema(description = "Body contains (case-insensitive).", example = "registered")
  private String bodyContains;

  @Schema(
      description = "Created at from (inclusive), ISO-8601.",
      example = "2026-05-01T00:00:00",
      type = "string",
      format = "date-time")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime createdFrom;

  @Schema(
      description = "Created at to (inclusive), ISO-8601.",
      example = "2026-05-28T23:59:59",
      type = "string",
      format = "date-time")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime createdTo;

  @Schema(
      description = "Sent at from (inclusive), ISO-8601.",
      example = "2026-05-01T00:00:00",
      type = "string",
      format = "date-time")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime sentFrom;

  @Schema(
      description = "Sent at to (inclusive), ISO-8601.",
      example = "2026-05-28T23:59:59",
      type = "string",
      format = "date-time")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime sentTo;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public NotificationChannel getChannel() {
    return channel;
  }

  public void setChannel(NotificationChannel channel) {
    this.channel = channel;
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

  public String getRecipientEmailContains() {
    return recipientEmailContains;
  }

  public void setRecipientEmailContains(String recipientEmailContains) {
    this.recipientEmailContains = recipientEmailContains;
  }

  public String getRecipientPhoneContains() {
    return recipientPhoneContains;
  }

  public void setRecipientPhoneContains(String recipientPhoneContains) {
    this.recipientPhoneContains = recipientPhoneContains;
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

  public LocalDateTime getCreatedFrom() {
    return createdFrom;
  }

  public void setCreatedFrom(LocalDateTime createdFrom) {
    this.createdFrom = createdFrom;
  }

  public LocalDateTime getCreatedTo() {
    return createdTo;
  }

  public void setCreatedTo(LocalDateTime createdTo) {
    this.createdTo = createdTo;
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
