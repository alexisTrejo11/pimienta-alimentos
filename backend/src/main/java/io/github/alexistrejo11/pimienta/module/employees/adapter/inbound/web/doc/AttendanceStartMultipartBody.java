package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.request.StartWorkdayRequest;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * OpenAPI-only shape of {@code multipart/form-data} for check-in. Not used at runtime; documents
 * parts {@code attendance} (JSON) and optional {@code checkInEvidencePhoto} (binary).
 */
@Schema(
    name = "AttendanceStartMultipartBody",
    description =
        "Send as **multipart/form-data**: part **attendance** = JSON (**StartWorkdayRequest**), optional "
            + "part **checkInEvidencePhoto** = image file.")
public record AttendanceStartMultipartBody(
    @Schema(
        description =
            "JSON payload; prefer Content-Type **application/json** on the part. The server also "
                + "accepts UTF-8 JSON if the part is sent as generic binary.",
        requiredMode = Schema.RequiredMode.REQUIRED,
        implementation = StartWorkdayRequest.class)
    StartWorkdayRequest attendance,
    @Schema(
        description = "Optional check-in evidence image (e.g. JPEG or PNG).",
        type = "string",
        format = "binary",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    byte[] checkInEvidencePhoto) {}
