import { isPlatformBrowser } from '@angular/common';
import {
  HttpBackend,
  HttpClient,
  HttpContextToken,
  HttpErrorResponse,
  HttpInterceptorFn,
} from '@angular/common/http';
import { inject, PLATFORM_ID } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, catchError, finalize, shareReplay, switchMap, throwError } from 'rxjs';

import { API_BASE_URL } from '../config/api.config';
import type { TokenResponse } from '../model/account/auth.dto';

/**
 * When true, this request already ran after a token refresh; do not refresh again (avoids loops).
 */
export const AUTH_RETRY_AFTER_REFRESH = new HttpContextToken<boolean>(() => false);

function isProtectedApiUrl(url: string): boolean {
  return url.includes('/api/v1/') && !url.includes('/api/v1/auth/');
}

let refreshInFlight: Observable<TokenResponse> | null = null;

function refreshTokens(http: HttpClient): Observable<TokenResponse> {
  if (!refreshInFlight) {
    const refreshToken = sessionStorage.getItem('refreshToken');
    if (!refreshToken) {
      return throwError(() => new Error('Missing refresh token'));
    }
    refreshInFlight = http
      .post<TokenResponse>(`${API_BASE_URL}/auth/refresh`, { refreshToken })
      .pipe(
        finalize(() => {
          refreshInFlight = null;
        }),
        shareReplay({ bufferSize: 1, refCount: true }),
      );
  }
  return refreshInFlight;
}

function clearSessionAndRedirect(router: Router, err: HttpErrorResponse): Observable<never> {
  sessionStorage.removeItem('accessToken');
  sessionStorage.removeItem('refreshToken');
  const returnUrl = router.url.startsWith('/app') ? router.url : undefined;
  void router.navigate(['/auth/login'], returnUrl ? { queryParams: { returnUrl } } : {});
  return throwError(() => err);
}

/**
 * On {@code 401} or {@code 403} from the Pimienta API (excluding {@code /auth/*}):
 * one refresh via {@code POST /auth/refresh}, then retries the original request once.
 * If refresh fails or the retry still fails, clears session and sends the user to login.
 *
 * Note: a true RBAC {@code 403} after a valid token will still end at login with this policy;
 * tighten later by inspecting {@code errorCode} if needed.
 */
export const authSessionInterceptor: HttpInterceptorFn = (req, next) => {
  const platformId = inject(PLATFORM_ID);
  if (!isPlatformBrowser(platformId) || !isProtectedApiUrl(req.url)) {
    return next(req);
  }

  const router = inject(Router);
  const httpBackend = inject(HttpBackend);
  const bareHttp = new HttpClient(httpBackend);

  return next(req).pipe(
    catchError((err: unknown) => {
      if (!(err instanceof HttpErrorResponse)) {
        return throwError(() => err);
      }
      const status = err.status;
      if (status !== 401 && status !== 403) {
        return throwError(() => err);
      }
      if (req.context.get(AUTH_RETRY_AFTER_REFRESH)) {
        return clearSessionAndRedirect(router, err);
      }
      const refreshToken = sessionStorage.getItem('refreshToken');
      if (!refreshToken) {
        return clearSessionAndRedirect(router, err);
      }

      return refreshTokens(bareHttp).pipe(
        switchMap((tokens) => {
          sessionStorage.setItem('accessToken', tokens.accessToken);
          sessionStorage.setItem('refreshToken', tokens.refreshToken);
          const retryReq = req.clone({
            context: req.context.set(AUTH_RETRY_AFTER_REFRESH, true),
          });
          return next(retryReq);
        }),
        catchError(() => clearSessionAndRedirect(router, err)),
      );
    }),
  );
};
