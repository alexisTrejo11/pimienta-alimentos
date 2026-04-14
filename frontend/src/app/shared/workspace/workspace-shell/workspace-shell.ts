import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { WorkspaceFooterComponent } from '../workspace-footer/workspace-footer';
import { WorkspaceSidebarComponent } from '../workspace-sidebar/workspace-sidebar';

@Component({
  selector: 'app-workspace-shell',
  standalone: true,
  imports: [RouterOutlet, WorkspaceSidebarComponent, WorkspaceFooterComponent],
  templateUrl: './workspace-shell.html',
})
export class WorkspaceShellComponent {}
