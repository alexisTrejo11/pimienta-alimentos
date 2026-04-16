package io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web;

import io.github.alexistrejo11.pimienta.module.crm.core.application.command.CreateOpportunityParams;
import io.github.alexistrejo11.pimienta.module.crm.core.application.summary.OpportunitySummary;
import io.github.alexistrejo11.pimienta.module.crm.core.port.input.OpportunityUseCases;
import io.github.alexistrejo11.pimienta.module.crm.core.application.query.OpportunitySearchCriteria;
import io.github.alexistrejo11.pimienta.module.crm.core.domain.Opportunity;
import io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.dto.CreateOpportunityRequest;
import io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.dto.LoseOpportunityRequest;
import io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.dto.OpportunityResponse;
import io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.dto.OpportunitySearchRequest;
import io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.dto.OpportunitySummaryResponse;
import io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.dto.UpdateOpportunityRequest;
import io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.dto.WinOpportunityRequest;
import io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.mapper.OpportunityWebMapper;
import io.github.alexistrejo11.pimienta.module.task.core.application.TaskManagementUseCases;
import io.github.alexistrejo11.pimienta.module.task.core.application.query.TaskSearchCriteria;
import io.github.alexistrejo11.pimienta.module.task.core.domain.CreateTaskParams;
import io.github.alexistrejo11.pimienta.module.task.core.domain.Task;
import io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.TaskManagerWebMapper;
import io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto.TaskListItemResponse;
import io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto.TaskRequest;
import io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto.TaskResponse;
import io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto.TaskSearchRequest;
import io.github.alexistrejo11.pimienta.shared.ratelimit.RateLimit;
import io.github.alexistrejo11.pimienta.shared.ratelimit.RateLimitProfile;
import io.github.alexistrejo11.pimienta.shared.web.PagedResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/opportunities")
@RateLimit(profile = RateLimitProfile.STANDARD)
public class OpportunityController {

  private final OpportunityUseCases opportunityUseCases;
  private final TaskManagementUseCases taskManagementUseCases;

  public OpportunityController(
      OpportunityUseCases opportunityUseCases, TaskManagementUseCases taskManagementUseCases) {
    this.opportunityUseCases = opportunityUseCases;
    this.taskManagementUseCases = taskManagementUseCases;
  }

  @GetMapping
  @RateLimit(profile = RateLimitProfile.READ_HEAVY)
  public PagedResponse<OpportunityResponse> searchOpportunities(
      @ModelAttribute OpportunitySearchRequest filter) {
    OpportunitySearchCriteria criteria = OpportunityWebMapper.toCriteria(filter);
    Pageable pageable = filter.toPageable();
    Page<Opportunity> page = opportunityUseCases.search(criteria, pageable);

    return PagedResponse.map(page, OpportunityWebMapper::toResponse);
  }

  @GetMapping("/{id}")
  public OpportunityResponse getOpportunity(@PathVariable Long id) {
    Opportunity opportunity = opportunityUseCases.getById(id);
    return OpportunityWebMapper.toResponse(opportunity);
  }

  @GetMapping("/{id}/summary")
  public OpportunitySummaryResponse getOpportunitySummary(@PathVariable Long id) {
    OpportunitySummary opportunitySummary = opportunityUseCases.getSummary(id);
    return OpportunityWebMapper.toSummaryResponse(opportunitySummary);
  }

  @GetMapping("/{id}/tasks")
  @RateLimit(profile = RateLimitProfile.READ_HEAVY)
  public PagedResponse<TaskListItemResponse> listOpportunityTasks(
      @PathVariable Long id, @ModelAttribute TaskSearchRequest filter) {
    // Validates exists
    opportunityUseCases.getById(id);

    filter.setOpportunityId(id);
    TaskSearchCriteria criteria = TaskManagerWebMapper.toCriteria(filter);
    Page<Task> page = taskManagementUseCases.search(criteria, filter.toPageable());

    return PagedResponse.map(page, TaskManagerWebMapper::toListItem);
  }

  @PostMapping("/{id}/tasks")
  @ResponseStatus(HttpStatus.CREATED)
  public TaskResponse createTaskForOpportunity(
      @PathVariable Long id, @Valid @RequestBody TaskRequest request) {
    // Validates exists
    opportunityUseCases.getById(id);

    CreateTaskParams params = TaskManagerWebMapper.toCreateParamsForOpportunity(request, id);
    Task created = taskManagementUseCases.create(params);

    return TaskManagerWebMapper.toResponse(created);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public OpportunityResponse createOpportunity(@Valid @RequestBody CreateOpportunityRequest request) {
    CreateOpportunityParams params = OpportunityWebMapper.toCreateParams(request);
    Opportunity created = opportunityUseCases.create(params);

    return OpportunityWebMapper.toResponse(created);
  }

  @PatchMapping("/{id}")
  public OpportunityResponse updateOpportunity(
      @PathVariable Long id, @Valid @RequestBody UpdateOpportunityRequest request) {
    Opportunity updated =
        opportunityUseCases.update(id, OpportunityWebMapper.toUpdateParams(request));
    return OpportunityWebMapper.toResponse(updated);
  }

  @DeleteMapping("/{id}")
  @RateLimit(profile = RateLimitProfile.SENSITIVE_OPERATIONS)
  public ResponseEntity<Void> deleteOpportunity(@PathVariable Long id) {
    opportunityUseCases.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}/pipeline/discovery")
  public OpportunityResponse moveToDiscovery(@PathVariable Long id) {
    Opportunity o = opportunityUseCases.moveToDiscovery(id);
    return OpportunityWebMapper.toResponse(o);
  }

  @PostMapping("/{id}/pipeline/proposal")
  public OpportunityResponse sendProposal(@PathVariable Long id) {
    Opportunity o = opportunityUseCases.sendProposal(id);
    return OpportunityWebMapper.toResponse(o);
  }

  @PostMapping("/{id}/pipeline/negotiation")
  public OpportunityResponse startNegotiation(@PathVariable Long id) {
    Opportunity o = opportunityUseCases.startNegotiation(id);
    return OpportunityWebMapper.toResponse(o);
  }

  @PostMapping("/{id}/win")
  @RateLimit(profile = RateLimitProfile.SENSITIVE_OPERATIONS)
  public OpportunityResponse winOpportunity(
      @PathVariable Long id, @Valid @RequestBody WinOpportunityRequest request) {
    Opportunity o = opportunityUseCases.win(id, OpportunityWebMapper.toWinParams(request));
    return OpportunityWebMapper.toResponse(o);
  }

  @PostMapping("/{id}/lose")
  @RateLimit(profile = RateLimitProfile.SENSITIVE_OPERATIONS)
  public OpportunityResponse loseOpportunity(
      @PathVariable Long id, @Valid @RequestBody LoseOpportunityRequest request) {
    Opportunity o = opportunityUseCases.lose(id, request.reason());
    return OpportunityWebMapper.toResponse(o);
  }

  @PostMapping("/{id}/abandon")
  @RateLimit(profile = RateLimitProfile.SENSITIVE_OPERATIONS)
  public OpportunityResponse abandonOpportunity(@PathVariable Long id) {
    Opportunity o = opportunityUseCases.abandon(id);
    return OpportunityWebMapper.toResponse(o);
  }

  @PostMapping("/{id}/reopen")
  public OpportunityResponse reopenOpportunity(@PathVariable Long id) {
    Opportunity o = opportunityUseCases.reopen(id);
    return OpportunityWebMapper.toResponse(o);
  }
}
