package io.github.alexistrejo11.pimienta.module.contract.adapter.out.persistence.repository;

import io.github.alexistrejo11.pimienta.module.contract.adapter.out.persistence.model.BusinessContractJpaEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractJpaRepository extends JpaRepository<BusinessContractJpaEntity, Long> {

  Optional<BusinessContractJpaEntity> findByIdAndDeletedAtIsNull(Long id);

  Page<BusinessContractJpaEntity> findAllByDeletedAtIsNull(Pageable pageable);
}
