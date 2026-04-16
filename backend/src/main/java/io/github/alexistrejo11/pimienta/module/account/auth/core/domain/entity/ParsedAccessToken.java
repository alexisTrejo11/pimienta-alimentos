package io.github.alexistrejo11.pimienta.module.account.auth.core.domain.entity;

import java.util.List;

public record ParsedAccessToken(
    Long userId,
    String email,
    String firstName,
    String lastName,
    String gender,
    List<String> roles,
    List<String> permissions,
    String jti) {}
