package io.github.alexistrejo11.pimienta.module.contract.adapter.inbound.web.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ExtendContractRequest(@NotNull LocalDate newEnd) {}
