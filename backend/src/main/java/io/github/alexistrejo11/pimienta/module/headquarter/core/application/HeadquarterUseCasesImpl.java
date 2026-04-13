package io.github.alexistrejo11.pimienta.module.headquarter.core.application;

import io.github.alexistrejo11.pimienta.module.headquarter.core.application.command.CreateHeadquarterCommand;
import io.github.alexistrejo11.pimienta.module.headquarter.core.application.command.UpdateHeadquarterCommand;
import io.github.alexistrejo11.pimienta.module.headquarter.core.domain.Headquarter;
import io.github.alexistrejo11.pimienta.module.headquarter.core.domain.HeadquarterStatistics;
import io.github.alexistrejo11.pimienta.module.headquarter.core.port.HeadquarterRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class HeadquarterUseCasesImpl implements HeadquarterUseCases {

  private final HeadquarterRepository headquarterRepository;

  public HeadquarterUseCasesImpl(HeadquarterRepository headquarterRepository) {
    this.headquarterRepository = headquarterRepository;
  }

  @Override
  public Page<Headquarter> getBy(Pageable pageable) {
    return headquarterRepository.findAll(pageable);
  }

  @Override
  public Headquarter getById(Long id) {
    return headquarterRepository
        .findById(id)
        .orElseThrow(() -> new HeadquarterNotFoundException(id));
  }

  @Override
  public Headquarter getByName(String name) {
    return headquarterRepository
        .findByName(name)
        .orElseThrow(() -> new HeadquarterNotFoundException(name));
  }

  @Override
  public Headquarter create(CreateHeadquarterCommand command) {
    return headquarterRepository.save(
        Headquarter.create(command.name(), command.address(), command.description()));
  }

  @Override
  public Headquarter update(Long id, UpdateHeadquarterCommand command) {
    return headquarterRepository.save(
        headquarterRepository
            .findById(id)
            .map(
                existing -> Headquarter.revise(
                    existing, command.name(), command.address(), command.description()))
            .orElseThrow(() -> new HeadquarterNotFoundException(id)));
  }

  @Override
  public void delete(Long id) {
    headquarterRepository
        .findById(id)
        .map(
            h -> {
              h.delete();
              return headquarterRepository.save(h);
            })
        .orElseThrow(() -> new HeadquarterNotFoundException(id));
  }

  @Override
  public HeadquarterStatistics statistics() {
    return headquarterRepository.statistics();
  }
}
