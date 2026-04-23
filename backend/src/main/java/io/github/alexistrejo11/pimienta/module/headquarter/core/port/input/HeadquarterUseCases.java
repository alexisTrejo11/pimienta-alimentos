package io.github.alexistrejo11.pimienta.module.headquarter.core.port.input;

import io.github.alexistrejo11.pimienta.module.headquarter.core.application.command.CreateHeadquarterCommand;
import io.github.alexistrejo11.pimienta.module.headquarter.core.application.command.UpdateHeadquarterCommand;
import io.github.alexistrejo11.pimienta.module.headquarter.core.domain.Headquarter;
import io.github.alexistrejo11.pimienta.module.headquarter.core.domain.HeadquarterStatistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HeadquarterUseCases {

  Page<Headquarter> getBy(Pageable pageable);

  Headquarter getById(Long id);

  Headquarter getByName(String name);

  Headquarter create(CreateHeadquarterCommand command);

  Headquarter update(Long id, UpdateHeadquarterCommand command);

  void delete(Long id);

  HeadquarterStatistics statistics();
}
