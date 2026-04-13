package io.github.alexistrejo11.pimienta.module.account.auth.infrastructure.adapter.inbound.web.dto;

public record TokenResponse(
    String accessToken,
    String refreshToken,
    String tokenType,
    long expiresInSeconds) {}
