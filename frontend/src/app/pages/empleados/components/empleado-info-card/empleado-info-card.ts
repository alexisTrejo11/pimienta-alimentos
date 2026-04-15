import { Component, input } from '@angular/core';

import type { EmployeeResponse } from '../../../../core/model/employee/employee.dto';

/**
 * Tarjeta de información personal del empleado.
 * Muestra: nombre, email, teléfono, dirección, RFC, CURP, NSS, CLABE bancaria.
 */
@Component({
  selector: 'app-empleado-info-card',
  standalone: true,
  templateUrl: './empleado-info-card.html',
})
export class EmpleadoInfoCardComponent {
  readonly empleado = input.required<EmployeeResponse>();
}
