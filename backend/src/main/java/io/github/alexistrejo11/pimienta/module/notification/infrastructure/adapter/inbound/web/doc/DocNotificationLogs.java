package io.github.alexistrejo11.pimienta.module.notification.infrastructure.adapter.inbound.web.doc;

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
    name = "Notification logs",
    description =
        """
        Manager-facing read-only log of today's LOG-channel notifications. Requires JWT with \
        **ROLE_MANAGER** or **ROLE_ADMIN**.""")
public @interface DocNotificationLogs {}
