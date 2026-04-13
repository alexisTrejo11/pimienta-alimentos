package io.github.alexistrejo11.pimienta.module.headquarter.infrastructure.adapter.inbound.web.dto;

import java.time.LocalDateTime;

public record HeadQuarterResponse(
        Long id,
        String name,
        String address,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,
        Long version) {
}
