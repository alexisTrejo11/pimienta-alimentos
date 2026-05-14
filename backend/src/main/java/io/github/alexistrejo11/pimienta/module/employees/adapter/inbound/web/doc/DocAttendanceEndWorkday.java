package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.AttendanceResponse;
import io.github.alexistrejo11.pimienta.shared.exception.api.ApiErrorResponse;
import io.github.alexistrejo11.pimienta.shared.web.openapi.doc.DocJwtSecured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.http.MediaType;

/** {@code POST .../attendance/end-workday} (recommended: multipart with JSON part + optional photo). */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Operation(
    summary = "End workday (check-out)",
    description =
        """
        Recommended: **`multipart/form-data`** parts:

        + **`attendance`**: JSON matching **`EndWorkdayRequest`** (`Content-Type`:
          **`application/json`** when possible).
        + **`checkOutEvidencePhoto`** (optional): image. Stored in S3; responses use **presigned HTTPS URLs**.

        For JSON **without** evidence photo only, **`application/json`** is also accepted (hidden in Swagger). \
        **409** when no open attendance exists (`ATTENDANCE_NOT_OPEN_FOR_CHECKOUT`).""")
@RequestBody(
    required = true,
    description =
        "Must be **multipart/form-data** with part **attendance** (JSON, EndWorkdayRequest) and optional "
            + "**checkOutEvidencePhoto** (file).",
    content =
        @Content(
            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
            schema =
                @Schema(
                    implementation = AttendanceEndMultipartBody.class,
                    requiredProperties = {"attendance"},
                    description =
                        "Two form parts: **attendance** (object) and optional **checkOutEvidencePhoto** (binary)."),
            encoding = {
              @Encoding(
                  name = "attendance",
                  contentType = MediaType.APPLICATION_JSON_VALUE),
              @Encoding(name = "checkOutEvidencePhoto")
            }))
@ApiResponse(
    responseCode = "200",
    description = "Updated.",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AttendanceResponse.class)))
@ApiResponse(
    responseCode = "409",
    description = "Conflict.",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ApiErrorResponse.class)))
public @interface DocAttendanceEndWorkday {}
