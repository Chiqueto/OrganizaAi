package com.organizaAi.OrganizaAi.dto.team;

import com.organizaAi.OrganizaAi.dto.tournament.TournamentSummary;
import com.organizaAi.OrganizaAi.dto.user.PlayerSummary;
import com.organizaAi.OrganizaAi.dto.user.UserSummary;

import java.util.List;

public record TeamResponse(
        String id,
        String name,
        UserSummary responsible,
        List<PlayerSummary> players,
        List<TournamentSummary> tournaments
) {}

