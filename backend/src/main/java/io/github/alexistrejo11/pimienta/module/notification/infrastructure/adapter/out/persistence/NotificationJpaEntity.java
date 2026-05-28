package io.github.alexistrejo11.pimienta.module.notification.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationChannel;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationStatus;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationType;
import io.github.alexistrejo11.pimienta.shared.jpa.BaseJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(
    name = "notifications",
    indexes = {
      @Index(name = "idx_notifications_created_at", columnList = "created_at"),
      @Index(name = "idx_notifications_channel", columnList = "channel"),
      @Index(name = "idx_notifications_correlation_id", columnList = "correlation_id")
    })
public class NotificationJpaEntity extends BaseJpaEntity {

  @Id
  @Column(nullable = false)
  private UUID id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 16)
  private NotificationChannel channel;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 64)
  private NotificationType type;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 16)
  private NotificationStatus status;

  @Column(name = "recipient_email", length = 320)
  private String recipientEmail;

  @Column(name = "recipient_phone", length = 32)
  private String recipientPhone;

  @Column(name = "recipient_display_name", length = 240)
  private String recipientDisplayName;

  @Column(name = "recipient_user_id")
  private Long recipientUserId;

  @Column(length = 500)
  private String subject;

  @Column(columnDefinition = "TEXT")
  private String body;

  @Column(name = "template_id", length = 120)
  private String templateId;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "template_variables", columnDefinition = "jsonb")
  private String templateVariablesJson;

  @Column(name = "correlation_id", length = 200)
  private String correlationId;

  @Column(name = "related_user_id")
  private Long relatedUserId;

  @Column(nullable = false, length = 16)
  private String locale = "en";

  @Column(name = "attempt_count", nullable = false)
  private int attemptCount;

  @Column(name = "last_error", length = 1000)
  private String lastError;

  @Column(name = "sent_at")
  private LocalDateTime sentAt;

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

  public String getRecipientEmail() {
    return recipientEmail;
  }

  public void setRecipientEmail(String recipientEmail) {
    this.recipientEmail = recipientEmail;
  }

  public String getRecipientPhone() {
    return recipientPhone;
  }

  public void setRecipientPhone(String recipientPhone) {
    this.recipientPhone = recipientPhone;
  }

  public String getRecipientDisplayName() {
    return recipientDisplayName;
  }

  public void setRecipientDisplayName(String recipientDisplayName) {
    this.recipientDisplayName = recipientDisplayName;
  }

  public Long getRecipientUserId() {
    return recipientUserId;
  }

  public void setRecipientUserId(Long recipientUserId) {
    this.recipientUserId = recipientUserId;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getTemplateId() {
    return templateId;
  }

  public void setTemplateId(String templateId) {
    this.templateId = templateId;
  }

  public String getTemplateVariablesJson() {
    return templateVariablesJson;
  }

  public void setTemplateVariablesJson(String templateVariablesJson) {
    this.templateVariablesJson = templateVariablesJson;
  }

  public String getCorrelationId() {
    return correlationId;
  }

  public void setCorrelationId(String correlationId) {
    this.correlationId = correlationId;
  }

  public Long getRelatedUserId() {
    return relatedUserId;
  }

  public void setRelatedUserId(Long relatedUserId) {
    this.relatedUserId = relatedUserId;
  }

  public String getLocale() {
    return locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }

  public int getAttemptCount() {
    return attemptCount;
  }

  public void setAttemptCount(int attemptCount) {
    this.attemptCount = attemptCount;
  }

  public String getLastError() {
    return lastError;
  }

  public void setLastError(String lastError) {
    this.lastError = lastError;
  }

  public LocalDateTime getSentAt() {
    return sentAt;
  }

  public void setSentAt(LocalDateTime sentAt) {
    this.sentAt = sentAt;
  }
}
