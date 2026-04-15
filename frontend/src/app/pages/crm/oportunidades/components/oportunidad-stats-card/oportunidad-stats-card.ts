import { Component, input } from '@angular/core';

import type { OpportunitySummaryResponse } from '../../../../../core/model/crm/opportunity.dto';

/**
 * Tarjeta de resumen estadístico de una oportunidad.
 * Muestra: total de tareas, tareas abiertas, valor ponderado y si está vencida.
 */
@Component({
  selector: 'app-oportunidad-stats-card',
  standalone: true,
  templateUrl: './oportunidad-stats-card.html',
})
export class OportunidadStatsCardComponent {
  readonly summary = input.required<OpportunitySummaryResponse>();

  formatMXN(value: number): string {
    return new Intl.NumberFormat('es-MX', { style: 'currency', currency: 'MXN', maximumFractionDigits: 0 }).format(value);
  }
}
