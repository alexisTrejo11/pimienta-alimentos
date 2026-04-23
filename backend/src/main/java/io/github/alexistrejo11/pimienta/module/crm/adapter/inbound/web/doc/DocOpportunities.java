package io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.doc;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** OpenAPI tag for {@code /api/v1/opportunities}. */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Tag(
    name = "CRM — Opportunities",
    description =
        """
        Sales opportunities: search, CRUD, Excel import/export, pipeline transitions (discovery → win/lose), \
        and nested tasks. Requires `Authorization: Bearer <access_token>`. Typical roles: **MANAGER**, \
        **ADMIN**, **SUPPORT** (as configured).""")
public @interface DocOpportunities {}
