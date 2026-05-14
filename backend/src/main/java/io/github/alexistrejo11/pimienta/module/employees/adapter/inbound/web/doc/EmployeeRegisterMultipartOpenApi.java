package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.request.RegisterEmployeeRequest;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * OpenAPI sketch of {@code multipart/form-data}; the controller parses the JSON part manually so
 * clients may send UTF-8 JSON even when {@code Content-Type} is {@code application/octet-stream}.
 */
@Schema(description = "`multipart/form-data` parts for employee registration.")
public abstract class EmployeeRegisterMultipartOpenApi {

  EmployeeRegisterMultipartOpenApi() {}

  @Schema(description = "Employee onboarding JSON.", implementation = RegisterEmployeeRequest.class)
  RegisterEmployeeRequest employee;

  @Schema(description = "Optional profile image.", type = "string", format = "binary")
  byte[] photo;
}
