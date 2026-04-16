package io.github.alexistrejo11.pimienta.module.crm.adapter.out.persistence.repository;

import java.util.Optional;

import io.github.alexistrejo11.pimienta.module.crm.adapter.out.persistence.model.OpportunityJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OpportunityJpaRepository
    extends JpaRepository<OpportunityJpaEntity, Long>, JpaSpecificationExecutor<OpportunityJpaEntity> {

  Optional<OpportunityJpaEntity> findByIdAndDeletedAtIsNull(Long id);
}
