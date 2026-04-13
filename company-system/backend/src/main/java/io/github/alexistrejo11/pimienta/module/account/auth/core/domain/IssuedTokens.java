package io.github.alexistrejo11.pimienta.module.account.auth.core.domain;

public record IssuedTokens(String accessToken, String refreshToken, long accessExpiresInSeconds) {}
