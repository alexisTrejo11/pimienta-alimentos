package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(
    name = "EndWorkdayRequest",
    description =
        """
        JSON part for check-out when using **multipart/form-data** (`attendance` part), or full body \
        for **application/json**. Evidence photos are uploaded as the **`checkOutEvidencePhoto`** \
        multipart part only (not a URL field).""")
public record EndWorkdayRequest(
    @Schema(
            description = "Optional calendar work date; defaults to today when omitted.",
            example = "2026-05-14")
        LocalDate workDate) {}
