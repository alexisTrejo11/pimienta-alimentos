package io.github.alexistrejo11.pimienta.module.notification.infrastructure.adapter.out.event;

import io.github.alexistrejo11.pimienta.module.notification.core.port.output.DomainEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringDomainEventPublisher implements DomainEventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public SpringDomainEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Override
  public void publish(Object event) {
    applicationEventPublisher.publishEvent(event);
  }
}
