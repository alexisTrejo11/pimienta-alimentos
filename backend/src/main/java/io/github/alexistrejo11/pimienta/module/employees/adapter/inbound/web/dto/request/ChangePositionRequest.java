package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ChangePositionRequest(@NotBlank String position) {}
