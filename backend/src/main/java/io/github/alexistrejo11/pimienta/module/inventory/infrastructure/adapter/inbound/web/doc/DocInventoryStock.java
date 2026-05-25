package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.doc;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** OpenAPI tag for {@code /api/v1/inventory/stock}. */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Tag(
    name = "Inventory — stock",
    description =
        """
        Existencias por artículo y ubicación. Requiere Bearer JWT. Errores típicos: \
        `INVENTORY_NOT_FOUND`, `INVENTORY_ALREADY_EXISTS`, `ITEM_NOT_FOUND`, `STORAGE_LOCATION_NOT_FOUND`.""")
public @interface DocInventoryStock {}
