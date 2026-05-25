package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.request.UpdateEmployeeRequest;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * OpenAPI-only shape of {@code multipart/form-data} for {@code PUT /api/v1/employees/{id}}. Not used
 * at runtime; documents parts {@code employee} (JSON) and optional {@code photo} (binary).
 */
@Schema(
    name = "EmployeeUpdateMultipartBody",
    description =
        "Send as **multipart/form-data**: required part **employee** = JSON (schema "
            + "**UpdateEmployeeRequest**), optional part **photo** = image file.")
public record EmployeeUpdateMultipartBody(
    @Schema(
        description =
            "JSON payload for this part; prefer Content-Type **application/json** on the part. The "
                + "server also accepts UTF-8 JSON if the part is sent as generic binary.",
        requiredMode = Schema.RequiredMode.REQUIRED,
        implementation = UpdateEmployeeRequest.class)
    UpdateEmployeeRequest employee,
    @Schema(
        description = "Optional profile photo (e.g. JPEG or PNG).",
        type = "string",
        format = "binary",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    byte[] photo) {}
