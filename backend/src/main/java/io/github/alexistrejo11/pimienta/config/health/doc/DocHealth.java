package io.github.alexistrejo11.pimienta.config.health.doc;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** OpenAPI tag for {@code /api/v2/health}. */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Tag(
    name = "Health",
    description =
        "Public liveness checks. No authentication required; intended for load balancers, "
            + "container orchestrators, and uptime monitors.")
public @interface DocHealth {}
