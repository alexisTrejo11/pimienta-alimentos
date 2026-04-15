import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { API_BASE_URL } from '../config/api.config';
import type {
  EmployeeListItemResponse,
  EmployeeResponse,
  EmployeeStatisticsResponse,
  EmployeeSummaryResponse,
} from '../model/employee/employee.dto';
import type { PagedResponse } from '../model/common/pagination';

/** Llamadas al módulo de empleados: /api/v1/employees */
@Injectable({ providedIn: 'root' })
export class EmployeeService {
  private readonly http = inject(HttpClient);
  private readonly base = `${API_BASE_URL}/employees`;

  /**
   * Lista paginada de empleados con campos resumidos.
   * El bearer token se añade automáticamente por el interceptor.
   */
  list(page = 0, size = 20): Observable<PagedResponse<EmployeeListItemResponse>> {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get<PagedResponse<EmployeeListItemResponse>>(this.base, { params });
  }

  /** Detalle completo de un empleado por su ID. */
  getById(id: number): Observable<EmployeeResponse> {
    return this.http.get<EmployeeResponse>(`${this.base}/${id}`);
  }

  /** Estadísticas globales: total, activos, inactivos. */
  statistics(): Observable<EmployeeStatisticsResponse> {
    return this.http.get<EmployeeStatisticsResponse>(`${this.base}/statistics`);
  }

  /** Resumen por departamento: headcount desglosado. */
  summary(): Observable<EmployeeSummaryResponse> {
    return this.http.get<EmployeeSummaryResponse>(`${this.base}/summary`);
  }
}
