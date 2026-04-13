package io.github.alexistrejo11.pimienta.module.headquarter.infrastructure.adapter.inbound.web;

import io.github.alexistrejo11.pimienta.module.headquarter.core.application.command.CreateHeadquarterCommand;
import io.github.alexistrejo11.pimienta.module.headquarter.core.domain.Headquarter;
import io.github.alexistrejo11.pimienta.module.headquarter.core.domain.HeadquarterStatistics;
import io.github.alexistrejo11.pimienta.module.headquarter.core.application.command.UpdateHeadquarterCommand;
import io.github.alexistrejo11.pimienta.module.headquarter.infrastructure.adapter.inbound.web.dto.HeadQuarterResponse;
import io.github.alexistrejo11.pimienta.module.headquarter.infrastructure.adapter.inbound.web.dto.HeadquarterStatisticsResponse;
import io.github.alexistrejo11.pimienta.module.headquarter.infrastructure.adapter.inbound.web.dto.HeadQuarterRequest;

public final class HeadQuarterWebMapper {

  private HeadQuarterWebMapper() {
  }

  public static CreateHeadquarterCommand toCreateCommand(HeadQuarterRequest request) {
    return new CreateHeadquarterCommand(request.name(), request.address(), request.description());
  }

  public static UpdateHeadquarterCommand toUpdateCommand(HeadQuarterRequest request) {
    return new UpdateHeadquarterCommand(request.name(), request.address(), request.description());
  }

  public static HeadQuarterResponse toResponse(Headquarter headquarter) {
    return new HeadQuarterResponse(
        headquarter.getId(),
        headquarter.getName(),
        headquarter.getAddress(),
        headquarter.getDescription(),
        headquarter.getCreatedAt(),
        headquarter.getUpdatedAt(),
        headquarter.getDeletedAt(),
        headquarter.getVersion());
  }

  public static HeadquarterStatisticsResponse toResponse(HeadquarterStatistics statistics) {
    return new HeadquarterStatisticsResponse(
        statistics.total(), statistics.active(), statistics.softDeleted());
  }
}
