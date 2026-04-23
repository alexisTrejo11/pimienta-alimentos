package io.github.alexistrejo11.pimienta.module.account.auth.infrastructure.adapter.inbound.web.doc;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** OpenAPI tag for {@code /api/v1/auth}. */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Tag(
    name = "Authentication",
    description =
        "Registration and session endpoints. **Register** and **login** are public (no Bearer token). "
            + "**Refresh** and **logout** are also reachable without an `Authorization` header in this "
            + "configuration; they use the refresh token carried in the JSON body where applicable.")
public @interface DocAuth {}
