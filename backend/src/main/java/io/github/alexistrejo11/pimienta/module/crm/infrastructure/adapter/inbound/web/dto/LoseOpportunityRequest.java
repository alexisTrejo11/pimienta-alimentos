package io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.inbound.web.dto;

import jakarta.validation.constraints.NotBlank;

public record LoseOpportunityRequest(@NotBlank String reason) {}
