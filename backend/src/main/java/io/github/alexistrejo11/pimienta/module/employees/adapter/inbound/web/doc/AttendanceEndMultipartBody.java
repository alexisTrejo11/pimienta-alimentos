package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.request.EndWorkdayRequest;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * OpenAPI-only shape of {@code multipart/form-data} for check-out. Not used at runtime; documents
 * parts {@code attendance} (JSON) and optional {@code checkOutEvidencePhoto} (binary).
 */
@Schema(
    name = "AttendanceEndMultipartBody",
    description =
        "Send as **multipart/form-data**: part **attendance** = JSON (**EndWorkdayRequest**), optional "
            + "part **checkOutEvidencePhoto** = image file.")
public record AttendanceEndMultipartBody(
    @Schema(
        description =
            "JSON payload; prefer Content-Type **application/json** on the part. The server also "
                + "accepts UTF-8 JSON if the part is sent as generic binary.",
        requiredMode = Schema.RequiredMode.REQUIRED,
        implementation = EndWorkdayRequest.class)
    EndWorkdayRequest attendance,
    @Schema(
        description = "Optional check-out evidence image (e.g. JPEG or PNG).",
        type = "string",
        format = "binary",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    byte[] checkOutEvidencePhoto) {}
