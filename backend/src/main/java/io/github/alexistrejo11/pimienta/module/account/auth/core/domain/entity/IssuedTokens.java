package io.github.alexistrejo11.pimienta.module.account.auth.core.domain.entity;

public record IssuedTokens(String accessToken, String refreshToken, long accessExpiresInSeconds) {}
