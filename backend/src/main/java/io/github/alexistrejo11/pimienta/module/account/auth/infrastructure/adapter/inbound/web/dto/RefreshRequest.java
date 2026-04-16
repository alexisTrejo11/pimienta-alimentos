package io.github.alexistrejo11.pimienta.module.account.auth.infrastructure.adapter.inbound.web.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequest(@NotBlank String refreshToken) {
}
