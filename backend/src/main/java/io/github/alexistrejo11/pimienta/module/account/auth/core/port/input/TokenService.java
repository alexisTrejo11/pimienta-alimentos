package io.github.alexistrejo11.pimienta.module.account.auth.core.port.input;

import io.github.alexistrejo11.pimienta.module.account.auth.core.domain.entity.IssuedTokens;
import io.github.alexistrejo11.pimienta.module.account.auth.core.domain.entity.ParsedAccessToken;
import io.github.alexistrejo11.pimienta.module.account.auth.core.domain.entity.RefreshTokenValidation;
import io.github.alexistrejo11.pimienta.module.account.user.core.domain.entities.User;

public interface TokenService {

  IssuedTokens issuePair(User user);

  String issueAccessToken(User user);

  long accessTokenExpiresInSeconds();

  RefreshTokenValidation validateRefreshToken(String refreshToken);

  ParsedAccessToken parseAccessToken(String accessToken);

  String extractJtiFromRefreshToken(String refreshToken);
}
