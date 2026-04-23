package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.doc;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** OpenAPI tag for {@code /api/v1/tasks}. */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Tag(
    name = "Operations — Tasks",
    description =
        """
        Work items: search, CRUD, status and assignment updates, checklist toggles, and Excel \
        import/export. Requires `Authorization: Bearer <access_token>`. Typical roles: **MANAGER**, \
        **ADMIN**, **SUPPORT** (as configured). Not-found responses may use `TASK_NOT_FOUND` or \
        `TASK_CHECKLIST_ITEM_NOT_FOUND` in `ApiErrorResponse`.""")
public @interface DocTasks {}
