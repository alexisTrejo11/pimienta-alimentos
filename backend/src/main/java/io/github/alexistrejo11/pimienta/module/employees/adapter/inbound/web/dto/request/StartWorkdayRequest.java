package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(
    name = "StartWorkdayRequest",
    description =
        """
        JSON part for check-in when using **multipart/form-data** (`attendance` part), or full body \
        for **application/json**. Evidence photos are uploaded as the **`checkInEvidencePhoto`** \
        multipart part only (not a URL field).""")
public record StartWorkdayRequest(
    @NotNull
        @Schema(description = "Headquarter where the employee is clocking in.", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
        Long headquarterId,
    @Schema(
            description = "Optional calendar work date; defaults to today when omitted.",
            example = "2026-05-14")
        LocalDate workDate) {}
