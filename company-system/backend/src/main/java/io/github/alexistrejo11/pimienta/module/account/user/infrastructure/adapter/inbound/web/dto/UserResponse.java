package io.github.alexistrejo11.pimienta.module.account.user.infrastructure.adapter.inbound.web.dto;

import java.time.LocalDateTime;
import java.util.List;

public record UserResponse(
    Long id,
    String email,
    String displayName,
    boolean banned,
    String bannedReason,
    LocalDateTime bannedAt,
    List<String> roles,
    List<String> permissions,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
