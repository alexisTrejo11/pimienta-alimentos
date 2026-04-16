package io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web;

import io.github.alexistrejo11.pimienta.module.crm.core.port.input.ProjectMilestoneUseCases;
import io.github.alexistrejo11.pimienta.module.crm.core.domain.ProjectMilestone;
import io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.dto.CreateProjectMilestoneRequest;
import io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.dto.ProjectMilestoneResponse;
import io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.dto.UpdateProjectMilestoneRequest;
import io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.mapper.ProjectMilestoneWebMapper;
import io.github.alexistrejo11.pimienta.shared.ratelimit.RateLimit;
import io.github.alexistrejo11.pimienta.shared.ratelimit.RateLimitProfile;
import io.github.alexistrejo11.pimienta.shared.web.PageableRequest;
import io.github.alexistrejo11.pimienta.shared.web.PagedResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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
@RequestMapping("/api/v1/projects/{projectId}/milestones")
@RateLimit(profile = RateLimitProfile.STANDARD)
public class ProjectMilestoneController {

  private final ProjectMilestoneUseCases projectMilestoneUseCases;

  public ProjectMilestoneController(ProjectMilestoneUseCases projectMilestoneUseCases) {
    this.projectMilestoneUseCases = projectMilestoneUseCases;
  }

  @GetMapping
  @RateLimit(profile = RateLimitProfile.READ_HEAVY)
  public PagedResponse<ProjectMilestoneResponse> listMilestones(
      @PathVariable Long projectId,
      @ModelAttribute PageableRequest pageable) {
    Page<ProjectMilestone> milestonePage =
        projectMilestoneUseCases.listByProject(projectId, pageable.toPageable());
    return PagedResponse.map(milestonePage, ProjectMilestoneWebMapper::toResponse);
  }

  @GetMapping("/{milestoneId}")
  public ProjectMilestoneResponse getMilestone(
      @PathVariable Long projectId, @PathVariable Long milestoneId) {
    ProjectMilestone m = projectMilestoneUseCases.getById(projectId, milestoneId);
    return ProjectMilestoneWebMapper.toResponse(m);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProjectMilestoneResponse createMilestone(
      @PathVariable Long projectId, @Valid @RequestBody CreateProjectMilestoneRequest request) {
    ProjectMilestone created =
        projectMilestoneUseCases.create(projectId, ProjectMilestoneWebMapper.toCreateParams(request));
    return ProjectMilestoneWebMapper.toResponse(created);
  }

  @PatchMapping("/{milestoneId}")
  public ProjectMilestoneResponse updateMilestone(
      @PathVariable Long projectId,
      @PathVariable Long milestoneId,
      @Valid @RequestBody UpdateProjectMilestoneRequest request) {
    ProjectMilestone updated =
        projectMilestoneUseCases.update(
            projectId, milestoneId, ProjectMilestoneWebMapper.toUpdateParams(request));
    return ProjectMilestoneWebMapper.toResponse(updated);
  }

  @DeleteMapping("/{milestoneId}")
  @RateLimit(profile = RateLimitProfile.SENSITIVE_OPERATIONS)
  public ResponseEntity<Void> deleteMilestone(
      @PathVariable Long projectId, @PathVariable Long milestoneId) {
    projectMilestoneUseCases.delete(projectId, milestoneId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{milestoneId}/start")
  public ProjectMilestoneResponse startMilestone(
      @PathVariable Long projectId, @PathVariable Long milestoneId) {
    ProjectMilestone m = projectMilestoneUseCases.start(projectId, milestoneId);
    return ProjectMilestoneWebMapper.toResponse(m);
  }

  @PostMapping("/{milestoneId}/complete")
  public ProjectMilestoneResponse completeMilestone(
      @PathVariable Long projectId, @PathVariable Long milestoneId) {
    ProjectMilestone m = projectMilestoneUseCases.complete(projectId, milestoneId);
    return ProjectMilestoneWebMapper.toResponse(m);
  }

  @PostMapping("/{milestoneId}/delayed")
  public ProjectMilestoneResponse markDelayed(
      @PathVariable Long projectId, @PathVariable Long milestoneId) {
    ProjectMilestone m = projectMilestoneUseCases.markDelayed(projectId, milestoneId);
    return ProjectMilestoneWebMapper.toResponse(m);
  }

  @PostMapping("/{milestoneId}/cancel")
  @RateLimit(profile = RateLimitProfile.SENSITIVE_OPERATIONS)
  public ProjectMilestoneResponse cancelMilestone(
      @PathVariable Long projectId, @PathVariable Long milestoneId) {
    ProjectMilestone m = projectMilestoneUseCases.cancel(projectId, milestoneId);
    return ProjectMilestoneWebMapper.toResponse(m);
  }

  @PostMapping("/{milestoneId}/billed")
  public ProjectMilestoneResponse markBilled(
      @PathVariable Long projectId, @PathVariable Long milestoneId) {
    ProjectMilestone m = projectMilestoneUseCases.markBilled(projectId, milestoneId);
    return ProjectMilestoneWebMapper.toResponse(m);
  }
}
