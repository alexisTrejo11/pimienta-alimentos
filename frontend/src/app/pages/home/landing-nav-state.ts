import { Injectable, signal } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class LandingNavState {
  readonly activeSection = signal<string>('inicio');
}
