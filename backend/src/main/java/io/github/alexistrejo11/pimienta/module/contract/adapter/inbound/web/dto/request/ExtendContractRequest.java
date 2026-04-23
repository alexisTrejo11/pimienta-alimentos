package io.github.alexistrejo11.pimienta.module.contract.adapter.inbound.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/** PUT /api/v1/contracts/{id}/extend — nueva fecha de término manual. */
@Schema(
    name = "ExtendContractRequest",
    description =
        """
        Solicitud de prórroga: la nueva fecha de fin debe ser **estrictamente posterior** al \
        `effectiveEnd` actual. Solo aplica a contratos **FIXED_TERM**.""")
public record ExtendContractRequest(
    @NotNull
        @Schema(
            description = "Nueva fecha de fin de vigencia (ISO-8601 date), después del fin actual.",
            type = "string",
            format = "date",
            example = "2027-12-31")
    LocalDate newEnd) {}
