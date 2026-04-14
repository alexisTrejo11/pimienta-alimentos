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

export const routes: Routes = [
  {
    path: 'app',
    component: WorkspaceShellComponent,
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
      { path: 'dashboard', component: DashboardPageComponent },
      { path: 'clients', component: ClientsPageComponent },
      { path: 'inventory', component: InventoryPageComponent },
      { path: 'tasks', component: TasksPageComponent },
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
