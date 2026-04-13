package io.github.alexistrejo11.pimienta.module.headquarter.infrastructure.adapter.inbound.web;

import io.github.alexistrejo11.pimienta.module.headquarter.core.application.HeadquarterUseCases;
import io.github.alexistrejo11.pimienta.module.headquarter.core.application.command.CreateHeadquarterCommand;
import io.github.alexistrejo11.pimienta.module.headquarter.core.application.command.UpdateHeadquarterCommand;
import io.github.alexistrejo11.pimienta.module.headquarter.core.domain.Headquarter;
import io.github.alexistrejo11.pimienta.module.headquarter.core.domain.HeadquarterStatistics;
import io.github.alexistrejo11.pimienta.module.headquarter.infrastructure.adapter.inbound.web.dto.HeadQuarterRequest;
import io.github.alexistrejo11.pimienta.module.headquarter.infrastructure.adapter.inbound.web.dto.HeadQuarterResponse;
import io.github.alexistrejo11.pimienta.module.headquarter.infrastructure.adapter.inbound.web.dto.HeadquarterStatisticsResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/headquarters")
public class HeadQuarterController {

  private final HeadquarterUseCases headquarterUseCases;

  public HeadQuarterController(HeadquarterUseCases headquarterUseCases) {
    this.headquarterUseCases = headquarterUseCases;
  }

  @GetMapping("/statistics")
  public HeadquarterStatisticsResponse getHeadquarterStatistics() {
    HeadquarterStatistics statistics = headquarterUseCases.statistics();
    return HeadQuarterWebMapper.toResponse(statistics);
  }

  @GetMapping
  public Page<HeadQuarterResponse> getAllHeadquarters(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size) {
    Page<Headquarter> headquarters = headquarterUseCases.getBy(PageRequest.of(page, size));
    return headquarters.map(HeadQuarterWebMapper::toResponse);
  }

  @GetMapping("/{id}")
  public HeadQuarterResponse getHeadquarterById(@PathVariable Long id) {
    Headquarter headquarter = headquarterUseCases.getById(id);
    return HeadQuarterWebMapper.toResponse(headquarter);
  }

  @GetMapping("/name/{name}")
  public HeadQuarterResponse getHeadquarterByName(@PathVariable String name) {
    Headquarter headquarter = headquarterUseCases.getByName(name);
    return HeadQuarterWebMapper.toResponse(headquarter);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public HeadQuarterResponse createHeadquarter(@Valid @RequestBody HeadQuarterRequest request) {
    CreateHeadquarterCommand command = HeadQuarterWebMapper.toCreateCommand(request);
    Headquarter headquarter = headquarterUseCases.create(command);
    return HeadQuarterWebMapper.toResponse(headquarter);
  }

  @PutMapping("/{id}")
  public HeadQuarterResponse updateHeadquarter(
      @PathVariable Long id,
      @Valid @RequestBody HeadQuarterRequest request) {
    UpdateHeadquarterCommand command = HeadQuarterWebMapper.toUpdateCommand(request);
    Headquarter headquarter = headquarterUseCases.update(id, command);
    return HeadQuarterWebMapper.toResponse(headquarter);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> softDeleteHeadquarter(@PathVariable Long id) {
    headquarterUseCases.delete(id);
    return ResponseEntity.noContent().build();
  }
}
