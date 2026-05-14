package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Tag(
    name = "HR — Employee attendance",
    description =
        """
        Check-in / check-out for employees, queries by id, by headquarter for the current day, \
        paginated search, and automatic closure of forgotten open attendances (scheduled job). \
        Photo fields accept caller-managed URLs. Requires `Authorization: Bearer <access_token>` \
        unless this environment still permits the employees route for testing.""")
public @interface DocEmployeeAttendances {}
