package io.github.alexistrejo11.pimienta.config.health.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "HealthResponse", description = "Public liveness probe for load balancers and orchestrators.")
public record HealthResponse(
    @Schema(description = "Application health status.", example = "UP") String status,
    @Schema(description = "Spring application name.", example = "pimienta") String service) {}
