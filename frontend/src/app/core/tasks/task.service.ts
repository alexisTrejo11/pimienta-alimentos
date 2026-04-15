import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { API_BASE_URL } from '../config/api.config';
import type { TaskListItemResponse, TaskResponse, TaskSearchParams } from '../model/task/task.dto';
import type { PagedResponse } from '../model/common/pagination';

/** Llamadas al módulo de tareas: /api/v1/tasks */
@Injectable({ providedIn: 'root' })
export class TaskService {
  private readonly http = inject(HttpClient);
  private readonly base = `${API_BASE_URL}/tasks`;

  /** Lista paginada de tareas con filtros opcionales. */
  list(params: TaskSearchParams = {}): Observable<PagedResponse<TaskListItemResponse>> {
    let httpParams = new HttpParams()
      .set('page', params.page ?? 0)
      .set('size', params.size ?? 20);
    if (params.status) httpParams = httpParams.set('status', params.status);
    if (params.headquarterId) httpParams = httpParams.set('headquarterId', params.headquarterId);
    if (params.projectId) httpParams = httpParams.set('projectId', params.projectId);
    if (params.opportunityId) httpParams = httpParams.set('opportunityId', params.opportunityId);
    return this.http.get<PagedResponse<TaskListItemResponse>>(this.base, { params: httpParams });
  }

  /** Detalle completo de una tarea, incluyendo checklist. */
  getById(id: number): Observable<TaskResponse> {
    return this.http.get<TaskResponse>(`${this.base}/${id}`);
  }
}
