package io.github.alexistrejo11.pimienta.module.files.infrastructure.adapter.inbound.web.doc;

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
    name = "File management",
    description =
        """
        Administrator API for uploading, searching, deleting, and downloading company files stored \
        in S3 (pimienta/sources/). Requires JWT with **ROLE_ADMIN**.""")
public @interface DocFileManagement {}
