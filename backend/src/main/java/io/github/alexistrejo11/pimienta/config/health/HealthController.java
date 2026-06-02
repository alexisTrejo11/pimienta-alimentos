package io.github.alexistrejo11.pimienta.config.health;

import io.github.alexistrejo11.pimienta.config.health.doc.DocHealth;
import io.github.alexistrejo11.pimienta.config.health.doc.DocHealthGet;
import io.github.alexistrejo11.pimienta.config.health.dto.HealthResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/health")
@DocHealth
public class HealthController {

  private final String serviceName;

  public HealthController(@Value("${spring.application.name:pimienta}") String serviceName) {
    this.serviceName = serviceName;
  }

  @GetMapping
  @DocHealthGet
  public HealthResponse health() {
    return new HealthResponse("UP", serviceName);
  }
}
