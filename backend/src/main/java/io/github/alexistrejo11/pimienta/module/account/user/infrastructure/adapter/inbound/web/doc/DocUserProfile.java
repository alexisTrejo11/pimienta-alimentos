package io.github.alexistrejo11.pimienta.module.account.user.infrastructure.adapter.inbound.web.doc;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** OpenAPI tag for {@code /api/v1/users} (current-user profile). */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Tag(
    name = "Account — Profile",
    description =
        "Current-user profile and dashboard. Requires `Authorization: Bearer <access_token>`. "
            + "JWT must map to a valid user; roles are exposed as `ROLE_*` authorities.")
public @interface DocUserProfile {}
