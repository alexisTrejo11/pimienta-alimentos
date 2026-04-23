package io.github.alexistrejo11.pimienta.module.crm.core.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OpportunityImportRow(
    int excelRowNumber,
    Long id,
    String title,
    String description,
    String contactName,
    String contactEmail,
    String contactPhone,
    String companyName,
    String companyLocation,
    String industry,
    BigDecimal estimatedValue,
    Integer probabilityPercent,
    String sourceRaw,
    LocalDate expectedCloseDate,
    Long assignedSalesmanId) {}
