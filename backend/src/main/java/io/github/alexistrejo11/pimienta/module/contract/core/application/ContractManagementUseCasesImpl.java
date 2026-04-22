package io.github.alexistrejo11.pimienta.module.contract.core.application;

import io.github.alexistrejo11.pimienta.module.contract.core.application.command.CreateContractCommand;
import io.github.alexistrejo11.pimienta.module.contract.core.application.command.UpdateContractCommand;
import io.github.alexistrejo11.pimienta.module.contract.core.domain.Contract;
import io.github.alexistrejo11.pimienta.module.contract.core.domain.enums.ContractCategory;
import io.github.alexistrejo11.pimienta.module.contract.core.domain.exception.ContractNotFoundException;
import io.github.alexistrejo11.pimienta.module.contract.core.port.ContractManagementUseCases;
import io.github.alexistrejo11.pimienta.module.contract.core.port.ContractRepository;
import io.github.alexistrejo11.pimienta.module.crm.core.domain.exception.OpportunityNotFoundException;
import io.github.alexistrejo11.pimienta.module.crm.core.domain.exception.ProjectNotFoundException;
import io.github.alexistrejo11.pimienta.module.crm.core.port.output.OpportunityRepository;
import io.github.alexistrejo11.pimienta.module.crm.core.port.output.ProjectRepository;
import io.github.alexistrejo11.pimienta.module.employees.core.port.input.EmployeeManagementUseCases;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContractManagementUseCasesImpl implements ContractManagementUseCases {

  private static final Logger log = LoggerFactory.getLogger(ContractManagementUseCasesImpl.class);

  private final ContractRepository contractRepository;
  private final EmployeeManagementUseCases employeeManagementUseCases;
  private final OpportunityRepository opportunityRepository;
  private final ProjectRepository projectRepository;

  public ContractManagementUseCasesImpl(
      ContractRepository contractRepository,
      EmployeeManagementUseCases employeeManagementUseCases,
      OpportunityRepository opportunityRepository,
      ProjectRepository projectRepository) {
    this.contractRepository = contractRepository;
    this.employeeManagementUseCases = employeeManagementUseCases;
    this.opportunityRepository = opportunityRepository;
    this.projectRepository = projectRepository;
  }

  @Override
  public Page<Contract> getBy(Pageable pageable) {
    log.debug(
        "list contracts query start page={} size={}",
        pageable != null ? pageable.getPageNumber() : null,
        pageable != null ? pageable.getPageSize() : null);

    Page<Contract> page = contractRepository.findActive(pageable);

    log.debug(
        "list contracts query complete totalElements={} numberOfElements={}",
        page.getTotalElements(),
        page.getNumberOfElements());
    return page;
  }

  @Override
  public Contract getById(Long id) {
    log.debug("get contract by id query start contractId={}", id);
    if (id == null || id <= 0) {
      throw new ContractNotFoundException(id != null ? id : 0L);
    }

    Contract contract = contractRepository.findById(id).orElseThrow(() -> new ContractNotFoundException(id));

    log.debug("get contract by id query complete contractId={}", contract.getId());
    return contract;
  }

  @Override
  @Transactional
  public Contract create(CreateContractCommand command) {
    log.info(
        "create contract start category={} employeeId={} opportunityId={} projectId={} termKind={} "
            + "effectiveStart={} effectiveEnd={} currencyCode={} renewalCycleMonths={}",
        command.category(),
        command.employeeId(),
        command.opportunityId(),
        command.projectId(),
        command.termKind(),
        command.effectiveStart(),
        command.effectiveEnd(),
        command.currencyCode(),
        command.renewalCycleMonths());

    validateRelatedEntities(command.employeeId(), command.opportunityId(), command.projectId());

    Contract contract = Contract.builder()
        .Name(command.name())
        .Description(command.description())
        .Category(command.category())
        .EmployeeId(command.employeeId())
        .OpportunityId(command.opportunityId())
        .ProjectId(command.projectId())
        .TermKind(command.termKind())
        .EffectiveStart(command.effectiveStart())
        .EffectiveEnd(command.effectiveEnd())
        .DocumentUrl(command.documentUrl())
        .TermsAndConditions(command.termsAndConditions())
        .ReferenceCode(command.referenceCode())
        .RenewalCycleMonths(command.renewalCycleMonths())
        .AgreedValue(command.agreedValue())
        .CurrencyCode(command.currencyCode())
        .register();

    Contract saved = contractRepository.save(contract);
    if (saved.getCategory() == ContractCategory.EMPLOYEE && saved.getEmployeeId() != null) {
      employeeManagementUseCases.activateEmploymentAfterContract(
          saved.getEmployeeId(), saved.getEffectiveStart());
    }

    log.info("create contract complete contractId={}", saved.getId());
    return saved;
  }

  @Override
  @Transactional
  public Contract update(Long id, UpdateContractCommand command) {
    log.info(
        "update contract start contractId={} category={} employeeId={} opportunityId={} projectId={} termKind={}",
        id,
        command.category(),
        command.employeeId(),
        command.opportunityId(),
        command.projectId(),
        command.termKind());

    validateRelatedEntities(command.employeeId(), command.opportunityId(), command.projectId());

    Contract existing = getById(id);
    Contract revised = Contract.builder()
        .Name(command.name() == null ? existing.getName() : command.name())
        .Description(command.description() == null ? existing.getDescription() : command.description())
        .Category(command.category() == null ? existing.getCategory() : command.category())
        .EmployeeId(command.employeeId() == null ? existing.getEmployeeId() : command.employeeId())
        .OpportunityId(command.opportunityId() == null ? existing.getOpportunityId() : command.opportunityId())
        .ProjectId(command.projectId() == null ? existing.getProjectId() : command.projectId())
        .TermKind(command.termKind() == null ? existing.getTermKind() : command.termKind())
        .EffectiveStart(command.effectiveStart() == null ? existing.getEffectiveStart() : command.effectiveStart())
        .EffectiveEnd(command.effectiveEnd() == null ? existing.getEffectiveEnd() : command.effectiveEnd())
        .DocumentUrl(command.documentUrl() == null ? existing.getDocumentUrl() : command.documentUrl())
        .TermsAndConditions(
            command.termsAndConditions() == null ? existing.getTermsAndConditions() : command.termsAndConditions())
        .ReferenceCode(command.referenceCode() == null ? existing.getReferenceCode() : command.referenceCode())
        .RenewalCycleMonths(
            command.renewalCycleMonths() == null ? existing.getRenewalCycleMonths() : command.renewalCycleMonths())
        .AgreedValue(command.agreedValue() == null ? existing.getAgreedValue() : command.agreedValue())
        .CurrencyCode(command.currencyCode() == null ? existing.getCurrencyCode() : command.currencyCode())
        .revise(existing);

    Contract saved = contractRepository.save(revised);

    log.info("update contract complete contractId={}", saved.getId());
    return saved;
  }

  @Override
  @Transactional
  public void delete(Long id) {
    log.info("delete contract start contractId={}", id);

    Contract contract = getById(id);
    contract.delete();

    contractRepository.save(contract);
    log.info("delete contract complete contractId={}", id);
  }

  @Override
  @Transactional
  public Contract renew(Long id) {
    log.info("renew contract start contractId={}", id);

    Contract contract = getById(id);
    contract.renew();

    Contract saved = contractRepository.save(contract);
    log.info("renew contract complete contractId={}", saved.getId());
    return saved;
  }

  @Override
  @Transactional
  public Contract extend(Long id, LocalDate newEnd) {
    log.info("extend contract start contractId={} newEnd={}", id, newEnd);

    Contract contract = getById(id);
    contract.extendTo(newEnd);

    Contract saved = contractRepository.save(contract);
    log.info("extend contract complete contractId={} effectiveEnd={}", saved.getId(), saved.getEffectiveEnd());

    return saved;
  }

  private void validateRelatedEntities(Long employeeId, Long opportunityId, Long projectId) {
    if (employeeId != null) {
      employeeManagementUseCases.getById(employeeId);
    }
    if (opportunityId != null) {
      opportunityRepository
          .findById(opportunityId)
          .orElseThrow(() -> new OpportunityNotFoundException(opportunityId));
    }
    if (projectId != null) {
      projectRepository
          .findById(projectId)
          .orElseThrow(() -> new ProjectNotFoundException(projectId));
    }
  }
}
