package io.github.alexistrejo11.pimienta.module.account.auth.core.port;

import io.github.alexistrejo11.pimienta.module.account.auth.core.domain.IssuedTokens;
import io.github.alexistrejo11.pimienta.module.account.auth.core.domain.ParsedAccessToken;
import io.github.alexistrejo11.pimienta.module.account.auth.core.domain.RefreshTokenValidation;
import io.github.alexistrejo11.pimienta.module.account.user.core.domain.User;

public interface TokenService {

  IssuedTokens issuePair(User user);

  RefreshTokenValidation validateRefreshToken(String refreshToken);

  ParsedAccessToken parseAccessToken(String accessToken);

  String extractJtiFromRefreshToken(String refreshToken);
}
