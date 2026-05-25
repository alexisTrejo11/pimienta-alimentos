package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.doc;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** OpenAPI tag for {@code /api/v1/inventory/transactions}. */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Tag(
    name = "Inventory — transactions",
    description =
        """
        Movimientos de inventario (compra, venta, transferencia, ajustes, flujo de aprobación). \
        Requiere Bearer JWT. Errores: `INVENTORY_TRANSACTION_NOT_FOUND`, \
        `INVENTORY_TRANSACTION_INVALID_STATE`, validaciones de negocio.""")
public @interface DocInventoryTransactions {}
