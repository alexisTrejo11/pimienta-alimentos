package io.github.alexistrejo11.pimienta.module.crm.core.application;

import io.github.alexistrejo11.pimienta.module.crm.core.application.query.OpportunitySearchCriteria;
import io.github.alexistrejo11.pimienta.module.crm.core.domain.Opportunity;
import io.github.alexistrejo11.pimienta.module.crm.core.domain.Project;
import io.github.alexistrejo11.pimienta.module.crm.core.port.OpportunityRepository;
import io.github.alexistrejo11.pimienta.module.crm.core.port.ProjectRepository;
import io.github.alexistrejo11.pimienta.module.task.core.port.TaskRepository;
import io.github.alexistrejo11.pimienta.shared.exception.ConflictException;
import io.github.alexistrejo11.pimienta.shared.exception.ErrorCode;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OpportunityUseCasesImpl implements OpportunityUseCases {

  private final OpportunityRepository opportunityRepository;
  private final ProjectRepository projectRepository;
  private final TaskRepository taskRepository;

  public OpportunityUseCasesImpl(
      OpportunityRepository opportunityRepository,
      ProjectRepository projectRepository,
      TaskRepository taskRepository) {
    this.opportunityRepository = opportunityRepository;
    this.projectRepository = projectRepository;
    this.taskRepository = taskRepository;
  }

  @Override
  public Page<Opportunity> search(OpportunitySearchCriteria criteria, Pageable pageable) {
    OpportunitySearchCriteria effective =
        criteria != null ? criteria : OpportunitySearchCriteria.empty();
    return opportunityRepository.search(effective, pageable);
  }

  @Override
  public Opportunity getById(Long id) {
    return opportunityRepository
        .findById(id)
        .orElseThrow(() -> new OpportunityNotFoundException(id));
  }

  @Override
  public Opportunity create(CreateOpportunityParams params) {
    Opportunity created =
        Opportunity.open(
            params.title(),
            params.description(),
            params.contactName(),
            params.contactEmail(),
            params.contactPhone(),
            params.companyName(),
            params.companyLocation(),
            params.industry(),
            params.estimatedValue(),
            params.probabilityPercent(),
            params.source(),
            params.expectedCloseDate(),
            params.assignedSalesmanId());
    return opportunityRepository.save(created);
  }

  @Override
  public Opportunity update(Long id, UpdateOpportunityParams params) {
    Opportunity o = getById(id);
    if (params.title() != null) {
      o.setTitle(params.title());
    }
    if (params.description() != null) {
      o.setDescription(params.description());
    }
    if (params.contactName() != null) {
      o.setContactName(params.contactName());
    }
    if (params.contactEmail() != null) {
      o.setContactEmail(params.contactEmail());
    }
    if (params.contactPhone() != null) {
      o.setContactPhone(params.contactPhone());
    }
    if (params.companyName() != null) {
      o.setCompanyName(params.companyName());
    }
    if (params.companyLocation() != null) {
      o.setCompanyLocation(params.companyLocation());
    }
    if (params.industry() != null) {
      o.setIndustry(params.industry());
    }
    if (params.estimatedValue() != null) {
      o.setEstimatedValue(params.estimatedValue());
    }
    if (params.probabilityPercent() != null) {
      o.setProbabilityPercent(params.probabilityPercent());
    }
    if (params.source() != null) {
      o.setSource(params.source());
    }
    if (params.expectedCloseDate() != null) {
      o.setExpectedCloseDate(params.expectedCloseDate());
    }
    if (params.assignedSalesmanId() != null) {
      o.setAssignedSalesmanId(params.assignedSalesmanId());
    }
    return opportunityRepository.save(o);
  }

  @Override
  public void delete(Long id) {
    Opportunity o = getById(id);
    o.softDelete();
    opportunityRepository.save(o);
  }

  @Override
  public OpportunitySummary getSummary(Long id) {
    Opportunity o = getById(id);
    long taskCount = taskRepository.countByOpportunityId(id);
    long openTaskCount = taskRepository.countOpenByOpportunityId(id);
    return new OpportunitySummary(
        o, taskCount, openTaskCount, o.getWeightedValue(), o.isOverdue());
  }

  @Override
  public Opportunity moveToDiscovery(Long id) {
    Opportunity o = getById(id);
    o.moveToDiscovery();
    return opportunityRepository.save(o);
  }

  @Override
  public Opportunity sendProposal(Long id) {
    Opportunity o = getById(id);
    o.sendProposal();
    return opportunityRepository.save(o);
  }

  @Override
  public Opportunity startNegotiation(Long id) {
    Opportunity o = getById(id);
    o.startNegotiation();
    return opportunityRepository.save(o);
  }

  @Override
  public Opportunity win(Long id, WinOpportunityParams params) {
    Opportunity o = getById(id);
    String code = params.projectCode().trim();
    if (projectRepository.findByProjectCode(code).isPresent()) {
      throw new ConflictException(
          ErrorCode.CONFLICT,
          "A project with this code already exists.",
          Map.of("projectCode", code),
          "Duplicate projectCode on win: " + code);
    }
    Project project =
        Project.create(
            code,
            params.projectName(),
            params.description(),
            params.clientId(),
            o.getId(),
            params.type(),
            params.priority(),
            params.projectManagerId(),
            params.assignedSalesmanId(),
            params.plannedStartDate(),
            params.plannedEndDate(),
            params.contractedValue(),
            params.estimatedCost());
    Project savedProject = projectRepository.save(project);
    o.win();
    o.setConvertedProjectId(savedProject.getId());
    return opportunityRepository.save(o);
  }

  @Override
  public Opportunity lose(Long id, String reason) {
    Opportunity o = getById(id);
    o.lose(reason);
    return opportunityRepository.save(o);
  }

  @Override
  public Opportunity abandon(Long id) {
    Opportunity o = getById(id);
    o.abandon();
    return opportunityRepository.save(o);
  }

  @Override
  public Opportunity reopen(Long id) {
    Opportunity o = getById(id);
    o.reopen();
    return opportunityRepository.save(o);
  }
}
