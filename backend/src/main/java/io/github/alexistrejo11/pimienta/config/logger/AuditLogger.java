package io.github.alexistrejo11.pimienta.config.logger;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class AuditLogger {
  private static final Logger log = LoggerFactory.getLogger("audit");
  // WARNING: ObjectMapper may be in a specfic class for
  // serialization/deserialization. Can fail if not used correctly.
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public void logAuditEvent(AuditEvent auditEvent) {
    try {
      String eventJson = OBJECT_MAPPER.writeValueAsString(auditEvent);
      log.info("AUDIT EVENT: {}", eventJson);
    } catch (Exception e) {
      log.error("Failed to log audit event", e);
    }
  }
}
