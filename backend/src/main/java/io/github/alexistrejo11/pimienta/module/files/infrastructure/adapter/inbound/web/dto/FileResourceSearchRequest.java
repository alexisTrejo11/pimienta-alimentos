package io.github.alexistrejo11.pimienta.module.files.infrastructure.adapter.inbound.web.dto;

import io.github.alexistrejo11.pimienta.shared.web.PageableRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Search within the RESOURCE category — callers must always supply a module.
 */
@Schema(description = "Filters for GET /api/v1/files/resources (RESOURCE category only).")
public class FileResourceSearchRequest extends PageableRequest {

  @NotBlank(message = "module is required")
  @Schema(description = "Module slug to query resources for.", example = "inventory", requiredMode = Schema.RequiredMode.REQUIRED)
  private String module;

  @Schema(description = "Filter by entity type.", example = "inventory-item")
  private String entityType;

  @Schema(description = "Filter by entity id.", example = "42")
  private Long entityId;

  @Schema(description = "Original filename contains (case-insensitive).", example = "report")
  private String originalNameContains;

  @Schema(description = "Content type contains.", example = "pdf")
  private String contentTypeContains;

  @Schema(
      description = "Created from (inclusive), ISO-8601.",
      example = "2026-05-01T00:00:00",
      type = "string",
      format = "date-time")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime createdFrom;

  @Schema(
      description = "Created to (inclusive), ISO-8601.",
      example = "2026-05-31T23:59:59",
      type = "string",
      format = "date-time")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime createdTo;

  public String getModule() { return module; }
  public void setModule(String module) { this.module = module; }

  public String getEntityType() { return entityType; }
  public void setEntityType(String entityType) { this.entityType = entityType; }

  public Long getEntityId() { return entityId; }
  public void setEntityId(Long entityId) { this.entityId = entityId; }

  public String getOriginalNameContains() { return originalNameContains; }
  public void setOriginalNameContains(String originalNameContains) { this.originalNameContains = originalNameContains; }

  public String getContentTypeContains() { return contentTypeContains; }
  public void setContentTypeContains(String contentTypeContains) { this.contentTypeContains = contentTypeContains; }

  public LocalDateTime getCreatedFrom() { return createdFrom; }
  public void setCreatedFrom(LocalDateTime createdFrom) { this.createdFrom = createdFrom; }

  public LocalDateTime getCreatedTo() { return createdTo; }
  public void setCreatedTo(LocalDateTime createdTo) { this.createdTo = createdTo; }
}
