package io.github.alexistrejo11.pimienta.config.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.github.alexistrejo11.pimienta.shared.web.openapi.annotations.OpenApiSecuritySchemes;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI pimientaOpenApi() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Pimienta Alimentos API")
                .version("v1")
                .description(
                    """
                    REST API for Pimienta Alimentos. \
                    Most endpoints require a Bearer JWT access token. \
                    Role claims are exposed as Spring authorities `ROLE_<NAME>` (e.g. `ROLE_ADMIN`, `ROLE_MANAGER`).""")
                .license(
                    new License()
                        .name("Apache 2.0")
                        .url("https://www.apache.org/licenses/LICENSE-2.0"))
                .contact(
                    new Contact()
                        .name("Pimienta Alimentos")
                        .url("https://github.com/alexistrejo11/pimienta")))
        .components(
            new Components()
                .addSecuritySchemes(
                    OpenApiSecuritySchemes.BEARER_JWT,
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description(
                            "Send `Authorization: Bearer <access_token>`. "
                                + "Obtain tokens via `POST /api/v1/auth/login` or `POST /api/v1/auth/refresh`.")));
  }
}
