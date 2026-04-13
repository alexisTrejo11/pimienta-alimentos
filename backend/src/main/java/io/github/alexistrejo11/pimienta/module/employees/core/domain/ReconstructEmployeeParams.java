package io.github.alexistrejo11.pimienta.module.employees.core.domain;

import java.time.LocalDateTime;

/**
 * Carga desde persistencia ({@link Employee#reconstruct(ReconstructEmployeeParams)}): sin reglas de
 * negocio adicionales; el mapper de repositorio arma este record desde filas/columnas.
 */
public record ReconstructEmployeeParams(
    Long id,
    PersonalProfile personal,
    OfficialIdentifiers officialIds,
    Employment employment,
    Compensation compensation,
    BenefitsProfile benefits,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt,
    Long version) {}
