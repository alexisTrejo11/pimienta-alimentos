package io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OpportunityJpaRepository
    extends JpaRepository<OpportunityJpaEntity, Long>, JpaSpecificationExecutor<OpportunityJpaEntity> {

  Optional<OpportunityJpaEntity> findByIdAndDeletedAtIsNull(Long id);
}
