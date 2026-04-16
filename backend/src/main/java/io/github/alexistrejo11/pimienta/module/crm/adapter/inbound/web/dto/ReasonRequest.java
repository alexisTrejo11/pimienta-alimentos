package io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.dto;

import jakarta.validation.constraints.NotBlank;

public record ReasonRequest(@NotBlank String reason) {}
