package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.doc;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** OpenAPI tag for {@code /api/v1/inventory/items}. */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Tag(
    name = "Inventory — items",
    description =
        """
        Catálogo de artículos (SKU, categoría, estado). Requiere `Authorization: Bearer <access_token>`. \
        Operadores típicos: **ADMIN**, **MANAGER**. Errores de dominio devuelven `ApiErrorResponse` \
        (p. ej. `ITEM_NOT_FOUND`, `ITEM_SKU_ALREADY_EXISTS`).""")
public @interface DocInventoryItems {}
