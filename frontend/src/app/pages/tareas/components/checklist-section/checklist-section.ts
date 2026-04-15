import { Component, input } from '@angular/core';

import type { ChecklistItemResponse } from '../../../../core/model/task/task.dto';

/**
 * Sección de checklist de solo lectura.
 * Muestra cada ítem con su estado (completado o pendiente).
 */
@Component({
  selector: 'app-checklist-section',
  standalone: true,
  templateUrl: './checklist-section.html',
})
export class ChecklistSectionComponent {
  readonly items = input.required<ChecklistItemResponse[]>();
}
