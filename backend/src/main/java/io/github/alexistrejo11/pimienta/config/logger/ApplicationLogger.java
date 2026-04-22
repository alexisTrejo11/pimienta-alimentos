package io.github.alexistrejo11.pimienta.config.logger;

import org.springframework.stereotype.Component;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ApplicationLogger {

  private final Logger log = LoggerFactory.getLogger(ApplicationLogger.class);

  public void logBusinessEvent(String eventType, String message, Object data) {
    log.info("BUSINESS_EVENT: type={}, message={}, data={}",
        eventType, message, data);
  }

  public void logPerformance(String operation, long durationMs) {
    log.info("PERFORMANCE: operation={}, durationMs={}", operation, durationMs);
  }

  public void logError(String context, Exception error, Map<String, Object> details) {
    log.error("ERROR: context={}, error={}, details={}",
        context, error.getMessage(), details);
  }
}
