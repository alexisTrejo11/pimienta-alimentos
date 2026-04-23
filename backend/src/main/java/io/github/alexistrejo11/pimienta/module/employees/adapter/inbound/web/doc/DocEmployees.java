package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** OpenAPI tag for {@code /api/v1/employees}. */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Tag(
    name = "HR — Employees",
    description =
        """
        Employee lifecycle: search, registration, updates, contract submission, termination/rehire, \
        position changes, Excel import/export, and statistics. Requires `Authorization: Bearer \
        <access_token>`. Typical roles: **MANAGER**, **ADMIN**, **HR** (as configured). Not-found \
        responses use `EMPLOYEE_NOT_FOUND` in `ApiErrorResponse`.""")
public @interface DocEmployees {}
