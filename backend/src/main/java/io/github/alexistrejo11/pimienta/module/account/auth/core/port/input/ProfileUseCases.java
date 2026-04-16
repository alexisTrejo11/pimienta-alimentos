package io.github.alexistrejo11.pimienta.module.account.auth.core.port.input;

import io.github.alexistrejo11.pimienta.module.account.auth.core.domain.entity.UserManagerDashboard;
import io.github.alexistrejo11.pimienta.module.account.user.core.application.command.UpdateProfileCommand;
import io.github.alexistrejo11.pimienta.module.account.user.core.domain.entities.User;

public interface ProfileUseCases {

  UserManagerDashboard getDashboard(long userId);

  User getProfile(long userId);

  User updateProfile(long userId, UpdateProfileCommand command);
}
