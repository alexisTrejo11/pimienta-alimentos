package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.request.RegisterEmployeeRequest;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * OpenAPI-only shape of {@code multipart/form-data} for {@code POST /api/v1/employees}. Not used at
 * runtime; documents fields {@code employee} (JSON) and optional {@code photo} (binary).
 */
@Schema(
    name = "EmployeeRegisterMultipartBody",
    description =
        "Send as **multipart/form-data**: required part **employee** = JSON (schema "
            + "**RegisterEmployeeRequest**), optional part **photo** = image file.")
public record EmployeeRegisterMultipartBody(
    @Schema(
        description =
            "JSON payload for this part; prefer Content-Type **application/json** on the part. The "
                + "server also accepts UTF-8 JSON if the part is sent as generic binary.",
        requiredMode = Schema.RequiredMode.REQUIRED,
        implementation = RegisterEmployeeRequest.class)
    RegisterEmployeeRequest employee,
    @Schema(
        description = "Optional profile photo (e.g. JPEG or PNG).",
        type = "string",
        format = "binary",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    byte[] photo) {}
