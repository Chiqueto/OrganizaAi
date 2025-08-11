package com.organizaAi.OrganizaAi.dto.team;

import java.util.List;

public record TeamResponse(
        String id,
        String name,
        UserSummary responsible,
        List<PlayerSummary> players,
        List<TournamentSummary> tournaments
) {}

public record UserSummary(String id, String name, String email) {}
public record PlayerSummary(String id, String name) {}
public record TournamentSummary(String id, String name) {}