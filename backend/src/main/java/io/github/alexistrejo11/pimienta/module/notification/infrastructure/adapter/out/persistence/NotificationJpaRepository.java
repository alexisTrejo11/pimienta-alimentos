package io.github.alexistrejo11.pimienta.module.notification.infrastructure.adapter.out.persistence;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NotificationJpaRepository
    extends JpaRepository<NotificationJpaEntity, UUID>, JpaSpecificationExecutor<NotificationJpaEntity> {}
