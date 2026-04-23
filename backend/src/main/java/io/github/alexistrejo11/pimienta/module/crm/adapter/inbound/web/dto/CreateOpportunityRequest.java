package io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.dto;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.Opportunity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(
    name = "CreateOpportunityRequest",
    description = "Payload to open a new sales opportunity (Jakarta format validation).")
public record CreateOpportunityRequest(
    @NotBlank @Schema(description = "Deal title.", example = "Enterprise rollout", requiredMode = Schema.RequiredMode.REQUIRED)
    String title,
    @Schema(description = "Longer description.", example = "Phased deployment over 6 months.")
    String description,
    @NotBlank @Schema(description = "Primary contact name.", requiredMode = Schema.RequiredMode.REQUIRED)
    String contactName,
    @NotBlank @Schema(description = "Contact email.", requiredMode = Schema.RequiredMode.REQUIRED)
    String contactEmail,
    @Schema(description = "Contact phone.", example = "+52-55-1234-5678")
    String contactPhone,
    @NotBlank @Schema(description = "Company / account name.", requiredMode = Schema.RequiredMode.REQUIRED)
    String companyName,
    @Schema(description = "City / region hint.", example = "Monterrey, NL")
    String companyLocation,
    @Schema(description = "Industry segment.", example = "Food & beverage")
    String industry,
    @NotNull @Schema(description = "Estimated deal value.", requiredMode = Schema.RequiredMode.REQUIRED)
    BigDecimal estimatedValue,
    @Schema(description = "Win probability 0–100.", example = "25", minimum = "0", maximum = "100")
    int probabilityPercent,
    @NotNull @Schema(description = "Lead source.", requiredMode = Schema.RequiredMode.REQUIRED)
    Opportunity.OpportunitySource source,
    @Schema(description = "Target close date.", type = "string", format = "date")
    LocalDate expectedCloseDate,
    @Schema(description = "Assigned salesperson (employee id).", example = "3")
    Long assignedSalesmanId) {}
