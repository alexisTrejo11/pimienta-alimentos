package io.github.alexistrejo11.pimienta.module.inventory.core.application.query;

import io.github.alexistrejo11.pimienta.module.inventory.core.domain.InventoryMovement.InventoryMovementType;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.InventoryMovement.MovementDirection;
import java.time.LocalDateTime;

public record InventoryMovementSearchCriteria(
    InventoryMovementType type,
    MovementDirection direction,
    Long itemId,
    Long locationId,
    LocalDateTime fromDate,
    LocalDateTime toDate) {

  public static InventoryMovementSearchCriteria empty() {
    return new InventoryMovementSearchCriteria(null, null, null, null, null, null);
  }
}
