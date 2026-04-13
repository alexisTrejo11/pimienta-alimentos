package io.github.alexistrejo11.pimienta.module.headquarter.infrastructure.adapter.out.persistence.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeadquarterJpaRepository extends JpaRepository<HeadquarterJpaEntity, Long> {

  Optional<HeadquarterJpaEntity> findByNameAndDeletedAtIsNull(String name);

  long countByDeletedAtIsNotNull();
}
