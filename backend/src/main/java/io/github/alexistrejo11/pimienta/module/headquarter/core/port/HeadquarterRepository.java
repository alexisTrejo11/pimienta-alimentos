package io.github.alexistrejo11.pimienta.module.headquarter.core.port;

import io.github.alexistrejo11.pimienta.module.headquarter.core.domain.Headquarter;
import io.github.alexistrejo11.pimienta.module.headquarter.core.domain.HeadquarterStatistics;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HeadquarterRepository {

  Page<Headquarter> findAll(Pageable pageable);

  Optional<Headquarter> findById(Long id);

  Optional<Headquarter> findByName(String name);

  Headquarter save(Headquarter headquarter);

  HeadquarterStatistics statistics();
}
