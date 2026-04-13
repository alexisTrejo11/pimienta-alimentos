package io.github.alexistrejo11.pimienta.module.headquarter.infrastructure.adapter.inbound.web.dto;

import jakarta.validation.constraints.NotBlank;

public record HeadQuarterRequest(
        @NotBlank(message = "Name is required") String name,
        @NotBlank(message = "Address is required") String address,
        @NotBlank(message = "Description is required") String description) {

}
