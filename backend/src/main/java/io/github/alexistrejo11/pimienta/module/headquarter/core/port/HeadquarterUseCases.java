package io.github.alexistrejo11.pimienta.module.headquarter.core.port;

import io.github.alexistrejo11.pimienta.module.headquarter.core.domain.Headquarter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HeadquarterUseCases {
  Page<Headquarter> getAllHeadquarters(Pageable pageable);

  Headquarter getHeadquarter();

  Headquarter createHeadquarter(Headquarter headquarter);

  Headquarter updateHeadquarter(Headquarter headquarter);

  Headquarter deleteHeadquarter(Headquarter headquarter);
}
