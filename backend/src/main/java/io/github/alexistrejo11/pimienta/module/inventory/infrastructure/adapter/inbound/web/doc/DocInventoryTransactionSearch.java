package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.inventory.core.domain.InventoryTransaction;
import io.github.alexistrejo11.pimienta.shared.web.PagedResponse;
import io.github.alexistrejo11.pimienta.shared.web.openapi.doc.DocJwtSecured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** {@code GET /api/v1/inventory/transactions} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Parameters({
  @Parameter(
      name = "page",
      in = ParameterIn.QUERY,
      schema = @Schema(type = "integer", defaultValue = "0", minimum = "0")),
  @Parameter(
      name = "size",
      in = ParameterIn.QUERY,
      schema = @Schema(type = "integer", defaultValue = "20", minimum = "1", maximum = "100")),
  @Parameter(
      name = "type",
      in = ParameterIn.QUERY,
      schema = @Schema(implementation = InventoryTransaction.TransactionType.class)),
  @Parameter(
      name = "status",
      in = ParameterIn.QUERY,
      schema = @Schema(implementation = InventoryTransaction.TransactionStatus.class)),
  @Parameter(
      name = "fromDate",
      in = ParameterIn.QUERY,
      description = "Inicio de rango (ISO-8601 date-time).",
      example = "2025-01-01T00:00:00"),
  @Parameter(
      name = "toDate",
      in = ParameterIn.QUERY,
      description = "Fin de rango (ISO-8601 date-time).",
      example = "2025-12-31T23:59:59"),
  @Parameter(name = "initiatedById", in = ParameterIn.QUERY, description = "Filtrar por usuario que inició.")
})
@Operation(
    summary = "Search inventory transactions",
    description = "Lista paginada. Rate limit: **READ_HEAVY**.")
@ApiResponse(
    responseCode = "200",
    description = "Página de transacciones.",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = PagedResponse.class)))
public @interface DocInventoryTransactionSearch {}
