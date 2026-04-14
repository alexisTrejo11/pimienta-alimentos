import { isPlatformBrowser } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import {
  AfterViewInit,
  Component,
  DestroyRef,
  PLATFORM_ID,
  inject,
  signal,
} from '@angular/core';
import {
  FormBuilder,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { RouterLink } from '@angular/router';
import { finalize, merge, fromEvent } from 'rxjs';

import { ABOUT_IMAGE_URL, HERO_IMAGE_URL } from '../brand';
import { CONTACT_ENDPOINT } from '../contact-endpoint';
import { LandingNavState } from '../landing-nav-state';

const SECTION_IDS = [
  'inicio',
  'nosotros',
  'historia',
  'filosofia',
  'contacto',
] as const;

@Component({
  selector: 'app-home-landing',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './home-landing.html',
})
export class HomeLanding implements AfterViewInit {
  private readonly fb = inject(FormBuilder);
  private readonly http = inject(HttpClient);
  private readonly navState = inject(LandingNavState);
  private readonly destroyRef = inject(DestroyRef);
  private readonly platformId = inject(PLATFORM_ID);

  readonly heroImageUrl = HERO_IMAGE_URL;
  readonly aboutImageUrl = ABOUT_IMAGE_URL;

  readonly form = this.fb.nonNullable.group({
    nombre: ['', Validators.required],
    empresa: [''],
    correo: ['', [Validators.required, Validators.email]],
    mensaje: ['', Validators.required],
  });

  readonly statusMessage = signal<string | null>(null);
  readonly statusType = signal<'success' | 'error' | null>(null);
  readonly isSubmitting = signal(false);

  ngAfterViewInit(): void {
    if (!isPlatformBrowser(this.platformId)) {
      return;
    }

    const sub = merge(
      fromEvent(window, 'scroll', { passive: true }),
      fromEvent(window, 'resize'),
    ).subscribe(() => this.updateActiveSection());
    this.destroyRef.onDestroy(() => sub.unsubscribe());

    queueMicrotask(() => this.updateActiveSection());
  }

  private updateActiveSection(): void {
    const ids = SECTION_IDS;
    if (!ids.length) {
      return;
    }

    const nav = document.querySelector('nav[aria-label="Principal"]');
    const navH = nav instanceof HTMLElement ? nav.offsetHeight : 0;
    const scrollY = window.scrollY;
    const maxScroll = Math.max(
      0,
      document.documentElement.scrollHeight - window.innerHeight,
    );

    if (scrollY + window.innerHeight >= maxScroll - 2) {
      this.navState.activeSection.set(ids[ids.length - 1]);
      return;
    }

    const marker = scrollY + navH + 2;
    let active: (typeof SECTION_IDS)[number] = ids[0];
    for (const id of ids) {
      const el = document.getElementById(id);
      if (!el) {
        continue;
      }
      if (el.offsetTop <= marker) {
        active = id;
      }
    }
    this.navState.activeSection.set(active);
  }

  onSubmit(): void {
    this.statusMessage.set(null);
    this.statusType.set(null);

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const { nombre, empresa, correo, mensaje } = this.form.getRawValue();
    const payload = {
      nombre: nombre.trim(),
      empresa: empresa.trim() || null,
      correo: correo.trim(),
      mensaje: mensaje.trim(),
    };

    this.isSubmitting.set(true);
    this.http
      .post<unknown>(CONTACT_ENDPOINT, payload, {
        headers: {
          Accept: 'application/json',
        },
      })
      .pipe(finalize(() => this.isSubmitting.set(false)))
      .subscribe({
        next: () => {
          this.statusType.set('success');
          this.statusMessage.set(
            'Gracias. Hemos recibido su mensaje; nos pondremos en contacto pronto.',
          );
          this.form.reset();
        },
        error: () => {
          this.statusType.set('error');
          this.statusMessage.set(
            'No se pudo enviar el mensaje. Compruebe su conexión o configure CONTACT_ENDPOINT.',
          );
        },
      });
  }

  clearStatusOnInput(): void {
    if (this.statusMessage()) {
      this.statusMessage.set(null);
      this.statusType.set(null);
    }
  }

  fieldError(field: 'nombre' | 'correo' | 'mensaje'): string | null {
    const c = this.form.controls[field];
    if (!c.touched && !c.dirty) {
      return null;
    }
    if (field === 'correo' && c.hasError('email')) {
      return 'Correo no válido.';
    }
    if (c.hasError('required')) {
      if (field === 'nombre') {
        return 'Ingrese su nombre.';
      }
      if (field === 'correo') {
        return 'Ingrese su correo.';
      }
      return 'Escriba un mensaje.';
    }
    return null;
  }

  controlClass(field: 'nombre' | 'correo' | 'mensaje'): string {
    const base =
      'w-full rounded-xl border border-transparent bg-surface-container-high px-6 py-4 font-body text-base text-[var(--color-on-background)] outline-none transition focus:border-[color-mix(in_srgb,var(--color-primary)_20%,transparent)] focus:shadow-[0_0_0_3px_color-mix(in_srgb,var(--color-primary)_15%,transparent)]';
    const textarea = field === 'mensaje' ? ' min-h-24 resize-none' : '';
    const err = this.fieldError(field)
      ? ' ring-2 ring-[color-mix(in_srgb,var(--color-error)_35%,transparent)]'
      : '';
    return `${base}${textarea}${err}`.trim();
  }

  statusAlertClass(): string {
    const t = this.statusType();
    if (t === 'success') {
      return 'mb-5 rounded-lg border border-green-600/25 bg-green-800/10 px-4 py-3.5 text-[0.9375rem] leading-snug text-green-800 dark:text-green-300';
    }
    if (t === 'error') {
      return 'mb-5 rounded-lg border border-[color-mix(in_srgb,var(--color-error)_25%,transparent)] bg-[color-mix(in_srgb,var(--color-error)_12%,transparent)] px-4 py-3.5 text-[0.9375rem] leading-snug text-red-900 dark:text-red-200';
    }
    return '';
  }
}
