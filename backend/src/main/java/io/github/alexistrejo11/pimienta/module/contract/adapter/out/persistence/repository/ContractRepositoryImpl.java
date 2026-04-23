package io.github.alexistrejo11.pimienta.module.contract.adapter.out.persistence.repository;

import io.github.alexistrejo11.pimienta.module.contract.adapter.out.persistence.ContractPersistenceMapper;
import io.github.alexistrejo11.pimienta.module.contract.adapter.out.persistence.model.BusinessContractJpaEntity;
import io.github.alexistrejo11.pimienta.module.contract.core.domain.Contract;
import io.github.alexistrejo11.pimienta.module.contract.core.port.ContractRepository;
import java.util.Objects;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ContractRepositoryImpl implements ContractRepository {

  private final ContractJpaRepository jpaRepository;
  private final ContractPersistenceMapper mapper;

  public ContractRepositoryImpl(ContractJpaRepository jpaRepository, ContractPersistenceMapper mapper) {
    this.jpaRepository = jpaRepository;
    this.mapper = mapper;
  }

  @Override
  public Optional<Contract> findById(long id) {
    if (id <= 0) {
      return Optional.empty();
    }
    return jpaRepository.findByIdAndDeletedAtIsNull(id).map(mapper::toDomain);
  }

  @Override
  public Page<Contract> findActive(Pageable pageable) {
    Objects.requireNonNull(pageable, "pageable");
    return jpaRepository.findAllByDeletedAtIsNull(pageable).map(mapper::toDomain);
  }

  @Override
  @Transactional
  public Contract save(Contract contract) {
    Objects.requireNonNull(contract, "contract");
    BusinessContractJpaEntity entity = mapper.toEntity(contract);
    BusinessContractJpaEntity saved = jpaRepository.save(entity);
    return mapper.toDomain(saved);
  }
}
