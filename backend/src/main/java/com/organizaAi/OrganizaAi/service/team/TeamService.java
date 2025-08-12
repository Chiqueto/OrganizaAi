package com.organizaAi.OrganizaAi.service.team;

import com.organizaAi.OrganizaAi.domain.Role;
import com.organizaAi.OrganizaAi.domain.Tournament;
import com.organizaAi.OrganizaAi.domain.User;
import com.organizaAi.OrganizaAi.domain.team.Team;
import com.organizaAi.OrganizaAi.dto.commom.ApiResponse;
import com.organizaAi.OrganizaAi.dto.team.TeamDTO;
import com.organizaAi.OrganizaAi.dto.team.TeamResponse;
import com.organizaAi.OrganizaAi.dto.user.PlayerSummary;
import com.organizaAi.OrganizaAi.dto.user.UserSummary;
import com.organizaAi.OrganizaAi.infra.exceptions.NotFoundException;
import com.organizaAi.OrganizaAi.infra.exceptions.UnauthorizedException;
import com.organizaAi.OrganizaAi.repository.TeamRepository;
import com.organizaAi.OrganizaAi.repository.TournamentRepository;
import com.organizaAi.OrganizaAi.repository.UserRepository;
import com.organizaAi.OrganizaAi.service.mapper.TeamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final UserRepository userRepository;
    private final TeamRepository repository;
    private final TournamentRepository tournamentRepository;
    private final TeamMapper teamMapper;
    @Transactional
    public TeamResponse createTeam(TeamDTO team){

        User responsible = userRepository.findById(team.responsible_id())
                .orElseThrow(() -> new NotFoundException("responsible_id",
                        "Usuário responsável não encontrado com o ID: " + team.responsible_id()));

        boolean allowed = responsible.getRoles().stream()
                .anyMatch(r -> r.getRole() == Role.COACH || r.getRole() == Role.ATHLETE);
        if (!allowed) {
            throw new UnauthorizedException("Você não tem permissão para criar um time");
        }

        // Carrega e valida players
        List<String> playerIds = team.players(); // ou UUID, conforme seu DTO
        List<User> players = userRepository.findAllById(playerIds);
        if (players.size() != playerIds.stream().distinct().count()) {
            throw new NotFoundException("players", "Um ou mais jogadores não existem.");
        }

        // Carrega e valida tournaments
        List<String> tournamentIds = team.tournaments();
        List<Tournament> tournaments = tournamentRepository.findAllById(tournamentIds);
        if (tournaments.size() != tournamentIds.stream().distinct().count()) {
            throw new NotFoundException("tournaments", "Um ou mais torneios não existem.");
        }

        Team newTeam = Team.builder()
                .name(team.name())
                .responsible(responsible)
                .players(players)
                .tournaments(tournaments)
                .inserted_at(LocalDateTime.now())
                .build();

       Team savedTeam = repository.save(newTeam);

       TeamResponse response = teamMapper.toTeamResponse(savedTeam);

       System.out.println(response);

        return response;

    }

}
