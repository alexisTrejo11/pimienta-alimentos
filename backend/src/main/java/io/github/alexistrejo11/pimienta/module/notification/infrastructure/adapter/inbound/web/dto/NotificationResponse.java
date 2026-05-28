package io.github.alexistrejo11.pimienta.module.notification.infrastructure.adapter.inbound.web.dto;

import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationChannel;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationStatus;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Schema(description = "Stored outbound notification record (audit log entry).")
public record NotificationResponse(
    @Schema(description = "Notification id.", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID id,
    @Schema(description = "Delivery channel.", example = "LOG") NotificationChannel channel,
    @Schema(description = "Business type.", example = "ACCOUNT_PENDING_APPROVAL") NotificationType type,
    @Schema(description = "Delivery status.", example = "SENT") NotificationStatus status,
    @Schema(description = "Recipient email when applicable.", example = "admin@example.com")
        String recipientEmail,
    @Schema(description = "Recipient phone when applicable.", example = "+573001234567")
        String recipientPhone,
    @Schema(description = "Recipient display name.", example = "Jane Admin") String recipientDisplayName,
    @Schema(description = "Recipient user id when linked to an account.", example = "1")
        Long recipientUserId,
    @Schema(description = "Subject line.", example = "New account pending approval") String subject,
    @Schema(description = "Message body.") String body,
    @Schema(description = "Template identifier.", example = "account-pending-approval-admin")
        String templateId,
    @Schema(description = "Template variables used for rendering.") Map<String, String> templateVariables,
    @Schema(description = "Correlation id grouping related sends.", example = "account-pending-approval:42")
        String correlationId,
    @Schema(description = "Related business user id.", example = "42") Long relatedUserId,
    @Schema(description = "Locale.", example = "en") String locale,
    @Schema(description = "Send attempts.", example = "1") int attemptCount,
    @Schema(description = "Last error message if failed.") String lastError,
    @Schema(
            description = "Created timestamp (ISO-8601 local).",
            example = "2026-05-28T10:15:00",
            type = "string",
            format = "date-time")
        LocalDateTime createdAt,
    @Schema(
            description = "Sent timestamp when status is SENT.",
            example = "2026-05-28T10:15:01",
            type = "string",
            format = "date-time")
        LocalDateTime sentAt) {}
