import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { finalize } from 'rxjs';

import { TaskService } from '../../../core/tasks/task.service';
import { parseApiError, type ParsedApiError } from '../../../core/http/parse-api-error';
import type { TaskResponse } from '../../../core/model/task/task.dto';
import { PageHeaderComponent } from '../../../shared/ui/page-header/page-header';
import { DataStateComponent } from '../../../shared/ui/data-state/data-state';
import { StatusBadgeComponent } from '../../../shared/ui/status-badge/status-badge';
import { ChecklistSectionComponent } from '../components/checklist-section/checklist-section';

/** Detalle completo de una tarea, incluyendo checklist. */
@Component({
  selector: 'app-tarea-detail-page',
  standalone: true,
  imports: [PageHeaderComponent, DataStateComponent, StatusBadgeComponent, ChecklistSectionComponent],
  templateUrl: './tarea-detail-page.html',
})
export class TareaDetailPageComponent implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly service = inject(TaskService);

  readonly loading = signal(true);
  readonly error = signal<ParsedApiError | null>(null);
  readonly tarea = signal<TaskResponse | null>(null);

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.cargar(id);
  }

  cargar(id: number): void {
    this.error.set(null);
    this.loading.set(true);
    this.service
      .getById(id)
      .pipe(finalize(() => this.loading.set(false)))
      .subscribe({
        next: (t) => this.tarea.set(t),
        error: (err: unknown) => this.error.set(parseApiError(err)),
      });
  }

  formatDate(iso: string | null): string {
    if (!iso) return '—';
    return new Date(iso).toLocaleDateString('es-MX', { day: '2-digit', month: 'long', year: 'numeric' });
  }

  get prioridadLabel(): string {
    const map: Record<string, string> = { LOW: 'Baja', MEDIUM: 'Media', HIGH: 'Alta', URGENT: 'Urgente' };
    return map[this.tarea()?.priority ?? ''] ?? '—';
  }
}
