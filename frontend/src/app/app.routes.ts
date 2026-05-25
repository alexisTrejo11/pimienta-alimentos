import { Routes } from '@angular/router';

import { AvisoPrivacidadPage } from './pages/home/aviso-privacidad-page/aviso-privacidad-page';
import { CalidadHigienicaPage } from './pages/home/calidad-higienica-page/calidad-higienica-page';
import { HomeLanding } from './pages/home/home-landing/home-landing';
import { Home } from './pages/home/home/home';
import { PortalPage } from './pages/home/portal-page/portal-page';
import { TerminosServicioPage } from './pages/home/terminos-servicio-page/terminos-servicio-page';
import { ClientsPageComponent } from './pages/clients/clients-page';
import { DashboardPageComponent } from './pages/dashboard/dashboard-page';
import { InventoryPageComponent } from './pages/inventory/inventory-page';
import { TasksPageComponent } from './pages/tasks/tasks-page';
import { WorkspaceShellComponent } from './shared/workspace/workspace-shell/workspace-shell';
import { Login } from './pages/auth/login/login';
import { Register } from './pages/auth/register/register';
import { workspaceAuthGuard } from './core/auth/workspace-auth.guard';

// ── Módulos nuevos ──────────────────────────────────────────────────────────
import { EmpleadosPageComponent } from './pages/empleados/empleados-page';
import { EmpleadoDetailPageComponent } from './pages/empleados/empleado-detail/empleado-detail-page';
import { OportunidadesPageComponent } from './pages/crm/oportunidades/oportunidades-page';
import { OportunidadDetailPageComponent } from './pages/crm/oportunidades/oportunidad-detail/oportunidad-detail-page';
import { OportunidadFormPageComponent } from './pages/crm/oportunidades/oportunidad-form-page/oportunidad-form-page';
import { ProyectosPageComponent } from './pages/crm/proyectos/proyectos-page';
import { ProyectoDetailPageComponent } from './pages/crm/proyectos/proyecto-detail/proyecto-detail-page';
import { ProyectoFormPageComponent } from './pages/crm/proyectos/proyecto-form-page/proyecto-form-page';
import { TareasPageComponent } from './pages/tareas/tareas-page';
import { TareaDetailPageComponent } from './pages/tareas/tarea-detail/tarea-detail-page';
import { TareaFormPageComponent } from './pages/tareas/tarea-form/tarea-form-page';
import { SedesPageComponent } from './pages/sedes/sedes-page';
import { SedeDetailPageComponent } from './pages/sedes/sede-detail/sede-detail-page';
import { ContratosPageComponent } from './pages/contratos/contratos-page';
import { NominaPageComponent } from './pages/nomina/nomina-page';

export const routes: Routes = [
  {
    path: 'auth/login',
    component: Login,
  },
  {
    path: 'auth/register',
    component: Register,
  },
  {
    path: 'app',
    component: WorkspaceShellComponent,
    canActivate: [workspaceAuthGuard],
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
      { path: 'dashboard', component: DashboardPageComponent },

      // Módulos originales (se mantienen para no romper referencias existentes)
      { path: 'clients', component: ClientsPageComponent },
      { path: 'inventory', component: InventoryPageComponent },
      { path: 'tasks', component: TasksPageComponent },

      // ── Empleados ────────────────────────────────────────────────────────
      { path: 'empleados', component: EmpleadosPageComponent },
      { path: 'empleados/:id', component: EmpleadoDetailPageComponent },

      // ── CRM: Oportunidades (rutas estáticas y edición antes de `:id` detalle) ─
      { path: 'crm/oportunidades/nueva', component: OportunidadFormPageComponent },
      { path: 'crm/oportunidades/:id/editar', component: OportunidadFormPageComponent },
      { path: 'crm/oportunidades/:id', component: OportunidadDetailPageComponent },
      { path: 'crm/oportunidades', component: OportunidadesPageComponent },

      // ── CRM: Proyectos ───────────────────────────────────────────────────
      { path: 'crm/proyectos/nuevo', component: ProyectoFormPageComponent },
      { path: 'crm/proyectos/:id/editar', component: ProyectoFormPageComponent },
      { path: 'crm/proyectos/:id', component: ProyectoDetailPageComponent },
      { path: 'crm/proyectos', component: ProyectosPageComponent },

      // ── Tareas ───────────────────────────────────────────────────────────
      { path: 'tareas/nueva', component: TareaFormPageComponent },
      { path: 'tareas/:id', component: TareaDetailPageComponent },
      { path: 'tareas', component: TareasPageComponent },

      // ── Sedes ────────────────────────────────────────────────────────────
      { path: 'sedes', component: SedesPageComponent },
      { path: 'sedes/:id', component: SedeDetailPageComponent },

      // ── Contratos ─────────────────────────────────────────────────────────
      { path: 'contratos', component: ContratosPageComponent },

      // ── Nómina ────────────────────────────────────────────────────────────
      { path: 'nomina', component: NominaPageComponent },
    ],
  },
  {
    path: '',
    component: Home,
    children: [
      { path: '', component: HomeLanding },
      { path: 'portal', component: PortalPage },
      { path: 'aviso-privacidad', component: AvisoPrivacidadPage },
      { path: 'terminos-servicio', component: TerminosServicioPage },
      { path: 'calidad-higienica', component: CalidadHigienicaPage },
    ],
  },
];
