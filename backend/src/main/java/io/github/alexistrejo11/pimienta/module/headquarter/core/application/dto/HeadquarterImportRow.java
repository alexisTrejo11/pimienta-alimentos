package io.github.alexistrejo11.pimienta.module.headquarter.core.application.dto;

public record HeadquarterImportRow(
    int excelRowNumber, Long id, String name, String address, String description) {}
