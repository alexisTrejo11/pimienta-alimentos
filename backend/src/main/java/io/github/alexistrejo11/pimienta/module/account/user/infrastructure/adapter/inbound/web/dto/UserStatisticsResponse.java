package io.github.alexistrejo11.pimienta.module.account.user.infrastructure.adapter.inbound.web.dto;

public record UserStatisticsResponse(long totalUsers, long activeUsers, long bannedUsers) {}
