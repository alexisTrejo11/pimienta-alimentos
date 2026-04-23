package io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.doc;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** OpenAPI tag for {@code /api/v1/projects/{projectId}/milestones}. */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Tag(
    name = "CRM — Project milestones",
    description =
        """
        Milestones under a project: CRUD and lifecycle (start, complete, delayed, cancel, billed). \
        Requires `Authorization: Bearer <access_token>`. **404** when project or milestone is unknown \
        (`PROJECT_NOT_FOUND`, `PROJECT_MILESTONE_NOT_FOUND`).""")
public @interface DocProjectMilestones {}
