package io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.crm.core.application.query.OpportunitySearchCriteria;
import io.github.alexistrejo11.pimienta.module.crm.core.domain.Opportunity;
import io.github.alexistrejo11.pimienta.module.crm.core.port.OpportunityRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public class OpportunityRepositoryImpl implements OpportunityRepository {

  private final OpportunityJpaRepository jpaRepository;

  public OpportunityRepositoryImpl(OpportunityJpaRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  @Override
  public Optional<Opportunity> findById(long id) {
    return jpaRepository.findByIdAndDeletedAtIsNull(id).map(OpportunityPersistenceMapper::toDomain);
  }

  @Override
  public Page<Opportunity> search(OpportunitySearchCriteria criteria, Pageable pageable) {
    OpportunitySearchCriteria effective =
        criteria != null ? criteria : OpportunitySearchCriteria.empty();
    Specification<OpportunityJpaEntity> spec = OpportunitySpecifications.fromCriteria(effective);
    return jpaRepository.findAll(spec, pageable).map(OpportunityPersistenceMapper::toDomain);
  }

  @Override
  public Opportunity save(Opportunity opportunity) {
    OpportunityJpaEntity entity = OpportunityPersistenceMapper.toJpa(opportunity);
    OpportunityJpaEntity saved = jpaRepository.save(entity);
    return OpportunityPersistenceMapper.toDomain(saved);
  }
}
