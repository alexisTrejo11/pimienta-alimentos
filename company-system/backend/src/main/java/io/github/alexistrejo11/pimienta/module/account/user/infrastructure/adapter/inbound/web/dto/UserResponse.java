package io.github.alexistrejo11.pimienta.module.account.user.infrastructure.adapter.inbound.web.dto;

import io.github.alexistrejo11.pimienta.module.account.user.core.domain.Gender;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record UserResponse(
    Long id,
    String email,
    String firstName,
    String lastName,
    Gender gender,
    String phone,
    LocalDate dateOfBirth,
    boolean banned,
    String bannedReason,
    LocalDateTime bannedAt,
    List<String> roles,
    List<String> permissions,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
