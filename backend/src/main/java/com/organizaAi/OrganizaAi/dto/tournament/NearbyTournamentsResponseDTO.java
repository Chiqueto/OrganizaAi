package com.organizaAi.OrganizaAi.dto.tournament;

import java.util.List;

public record NearbyTournamentsResponseDTO(
    int status,
    String message,
    List<GetTournamentDTO> tournaments
) {
}