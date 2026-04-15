import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { finalize } from 'rxjs';

import { AuthService } from '../../../core/auth/auth.service';
import {
  fieldMessage,
  parseApiError,
  type ParsedApiError,
} from '../../../core/http/parse-api-error';
import type { RegisterRequest } from '../../../core/model/account/auth.dto';
import type { Gender } from '../../../core/model/account/enums';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './register.html',
})
export class Register {
  private readonly fb = inject(FormBuilder);
  private readonly auth = inject(AuthService);
  private readonly router = inject(Router);

  /** Options for {@link RegisterRequest.gender}; labels are UI-only. */
  readonly genderOptions: { value: Gender; label: string }[] = [
    { value: 'MALE', label: 'Masculino' },
    { value: 'FEMALE', label: 'Femenino' },
    { value: 'NON_BINARY', label: 'No binario' },
    { value: 'OTHER', label: 'Otro' },
    { value: 'PREFER_NOT_TO_SAY', label: 'Prefiero no decir' },
  ];

  readonly submitting = signal(false);
  /** Set when the API returns an error (parsed {@link ParsedApiError} for template + logging). */
  readonly apiError = signal<ParsedApiError | null>(null);

  /**
   * Client-side constraints aligned with backend {@code RegisterRequest} / Bean Validation where possible.
   */
  readonly form = this.fb.nonNullable.group({
    firstName: ['', [Validators.required, Validators.maxLength(120)]],
    lastName: ['', [Validators.required, Validators.maxLength(120)]],
    gender: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
    phone: [
      '',
      [
        Validators.required,
        Validators.pattern(/^[+]?[0-9\s().-]{8,31}$/),
        Validators.maxLength(32),
      ],
    ],
    password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(32)]],
    dateOfBirth: ['', [Validators.required]],
  });

  submit(): void {
    this.apiError.set(null);
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    const v = this.form.getRawValue();
    const request: RegisterRequest = {
      firstName: v.firstName.trim(),
      lastName: v.lastName.trim(),
      gender: v.gender as Gender,
      email: v.email.trim().toLowerCase(),
      phone: v.phone.trim(),
      password: v.password,
      dateOfBirth: v.dateOfBirth,
    };

    this.submitting.set(true);

    // HttpClient returns an Observable: subscribe starts the request; finalize runs on success OR error.
    this.auth
      .register(request)
      .pipe(
        finalize(() => {
          this.submitting.set(false);
        }),
      )
      .subscribe({
        next: (tokens) => {
          console.info('[Register] cuenta creada', {
            expiresInSeconds: tokens.expiresInSeconds,
            tokenType: tokens.tokenType,
          });
          sessionStorage.setItem('accessToken', tokens.accessToken);
          sessionStorage.setItem('refreshToken', tokens.refreshToken);
          void this.router.navigateByUrl('/portal');
        },
        error: (err: unknown) => {
          const parsed = parseApiError(err);
          this.apiError.set(parsed);
          console.error('[Register] fallo el registro', { http: err, parsed });
        },
      });
  }

  /** Validation message from the last API response for a specific field (e.g. `email`). */
  apiFieldMessage(field: string): string | undefined {
    const p = this.apiError();
    if (!p) {
      return undefined;
    }
    return fieldMessage(p, field);
  }
}
