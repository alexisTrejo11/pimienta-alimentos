package io.github.alexistrejo11.pimienta.module.account.auth.core.domain;

import java.util.List;

public record ParsedAccessToken(
    Long userId,
    String email,
    List<String> roles,
    List<String> permissions,
    String jti) {}
