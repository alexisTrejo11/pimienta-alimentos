import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { forkJoin, finalize } from 'rxjs';

import { CrmService } from '../../../../core/crm/crm.service';
import { parseApiError, type ParsedApiError } from '../../../../core/http/parse-api-error';
import type { ProjectResponse, ProjectSummaryResponse } from '../../../../core/model/crm/project.dto';
import type { ProjectMilestoneResponse } from '../../../../core/model/crm/project-milestone.dto';
import { PageHeaderComponent } from '../../../../shared/ui/page-header/page-header';
import { DataStateComponent } from '../../../../shared/ui/data-state/data-state';
import { StatusBadgeComponent } from '../../../../shared/ui/status-badge/status-badge';
import { HitoItemComponent } from '../components/hito-item/hito-item';

/**
 * Detalle de un proyecto CRM.
 * Carga datos del proyecto, resumen e hitos en paralelo.
 */
@Component({
  selector: 'app-proyecto-detail-page',
  standalone: true,
  imports: [PageHeaderComponent, DataStateComponent, StatusBadgeComponent, HitoItemComponent],
  templateUrl: './proyecto-detail-page.html',
})
export class ProyectoDetailPageComponent implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly service = inject(CrmService);

  readonly loading = signal(true);
  readonly error = signal<ParsedApiError | null>(null);
  readonly proyecto = signal<ProjectResponse | null>(null);
  readonly summary = signal<ProjectSummaryResponse | null>(null);
  readonly hitos = signal<ProjectMilestoneResponse[]>([]);

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.cargar(id);
  }

  cargar(id: number): void {
    this.error.set(null);
    this.loading.set(true);
    forkJoin({
      proyecto: this.service.getProject(id),
      summary: this.service.getProjectSummary(id),
      hitos: this.service.listMilestones(id),
    })
      .pipe(finalize(() => this.loading.set(false)))
      .subscribe({
        next: ({ proyecto, summary, hitos }) => {
          this.proyecto.set(proyecto);
          this.summary.set(summary);
          this.hitos.set(hitos);
        },
        error: (err: unknown) => this.error.set(parseApiError(err)),
      });
  }

  formatDate(iso: string | null): string {
    if (!iso) return '—';
    const [y, m, d] = iso.split('-');
    return `${d}/${m}/${y}`;
  }

  formatMXN(value: number): string {
    return new Intl.NumberFormat('es-MX', { style: 'currency', currency: 'MXN', maximumFractionDigits: 0 }).format(value);
  }

  get tipoLabel(): string {
    const map: Record<string, string> = {
      CONSULTING: 'Consultoría',
      SOFTWARE_DEVELOPMENT: 'Desarrollo de software',
      IMPLEMENTATION: 'Implementación',
      MAINTENANCE: 'Mantenimiento',
      TRAINING: 'Capacitación',
      RESEARCH: 'Investigación',
      OTHER: 'Otro',
    };
    const tipo = this.proyecto()?.type ?? '';
    return map[tipo] ?? tipo;
  }
}
