package io.github.alexistrejo11.pimienta.module.task.core.domain;

/** Initial checklist line when assembling a task via {@link Task.SafeBuilder} (no validation). */
public record ChecklistDraft(String description, int displayOrder) {}
