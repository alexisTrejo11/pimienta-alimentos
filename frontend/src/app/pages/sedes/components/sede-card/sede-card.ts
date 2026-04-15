import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';

import type { HeadQuarterResponse } from '../../../../core/model/headquarter/headquarter.dto';

/**
 * Tarjeta de sede para el grid de listado.
 * Muestra nombre, dirección, descripción y si la sede está activa o dada de baja.
 */
@Component({
  selector: 'app-sede-card',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './sede-card.html',
})
export class SedeCardComponent {
  readonly sede = input.required<HeadQuarterResponse>();

  /** Una sede está activa si su campo deletedAt es null. */
  get activa(): boolean {
    return this.sede().deletedAt === null;
  }
}
