package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.doc;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** OpenAPI tag for {@code /api/v1/inventory/movements}. */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Tag(
    name = "Inventory — movements",
    description =
        """
        Historial de movimientos de stock (consulta). Requiere Bearer JWT. \
        **404**: `INVENTORY_MOVEMENT_NOT_FOUND`. Rate limit clase: **STANDARD**; lecturas: **READ_HEAVY**.""")
public @interface DocInventoryMovements {}
