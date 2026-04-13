package io.github.alexistrejo11.pimienta.module.employees.infrastructure.adapter.inbound.web.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangePositionRequest(@NotBlank String position) {}
