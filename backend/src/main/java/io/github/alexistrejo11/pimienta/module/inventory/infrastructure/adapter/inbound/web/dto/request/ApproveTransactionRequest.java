package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.dto.request;

import jakarta.validation.constraints.NotNull;

public record ApproveTransactionRequest(@NotNull Long approvedById) {}
