package io.github.alexistrejo11.pimienta.module.notification.core.port.output;

/**
 * Publishes application events to the local Spring event bus ({@code ApplicationEventPublisher}).
 */
public interface DomainEventPublisher {

  void publish(Object event);
}
