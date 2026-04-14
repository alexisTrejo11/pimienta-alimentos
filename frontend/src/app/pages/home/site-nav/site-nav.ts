import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

import { BRAND_LOGO_URL } from '../brand';
import { LandingNavState } from '../landing-nav-state';

@Component({
  selector: 'app-site-nav',
  imports: [RouterLink],
  templateUrl: './site-nav.html',
})
export class SiteNav {
  private readonly router = inject(Router);
  readonly navState = inject(LandingNavState);

  readonly logoUrl = BRAND_LOGO_URL;

  isLanding(): boolean {
    const path = this.router.url.split('?')[0].split('#')[0];
    return path === '/' || path === '';
  }

  navLinkClass(id: string): string {
    const base =
      'font-headline border-b-2 pb-1 text-sm font-bold tracking-tight transition-colors md:text-base';
    const inactive =
      'border-transparent text-stone-600 hover:text-primary dark:text-stone-400 dark:hover:text-red-400';
    const active = 'border-primary text-primary dark:border-red-400 dark:text-red-400';
    if (!this.isLanding()) {
      return `${base} ${inactive}`;
    }
    return `${base} ${this.navState.activeSection() === id ? active : inactive}`;
  }
}
