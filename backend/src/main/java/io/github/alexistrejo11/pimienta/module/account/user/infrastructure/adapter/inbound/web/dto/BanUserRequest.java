package io.github.alexistrejo11.pimienta.module.account.user.infrastructure.adapter.inbound.web.dto;

import jakarta.validation.constraints.Size;

public record BanUserRequest(@Size(max = 500) String reason) {}
