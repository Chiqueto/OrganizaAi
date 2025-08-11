package com.organizaAi.OrganizaAi.service.team;

import com.organizaAi.OrganizaAi.domain.Role;
import com.organizaAi.OrganizaAi.domain.Tournament;
import com.organizaAi.OrganizaAi.domain.User;
import com.organizaAi.OrganizaAi.domain.team.Team;
import com.organizaAi.OrganizaAi.dto.commom.ApiResponse;
import com.organizaAi.OrganizaAi.dto.team.TeamDTO;
import com.organizaAi.OrganizaAi.infra.exceptions.NotFoundException;
import com.organizaAi.OrganizaAi.infra.exceptions.UnauthorizedException;
import com.organizaAi.OrganizaAi.repository.TeamRepository;
import com.organizaAi.OrganizaAi.repository.TournamentRepository;
import com.organizaAi.OrganizaAi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    public Team createTeam(TeamDTO team){
        //Verificar se usuário existe
        User responsibleUser = userRepository.findById(team.responsible_id())
                .orElseThrow(() -> new NotFoundException("responsible_id" ,"Usuário responsável não encontrado com o ID: " + team.responsible_id()));

        if (responsibleUser.getRoles().stream()
                .noneMatch(role -> role.getRole().equals(Role.COACH) || role.getRole().equals(Role.ATHLETE))) {
            throw new UnauthorizedException("Você não tem permissão para criar um time");
        }

        List<User> players = userRepository.findAllById(team.players());
        List<Tournament> tournaments = tournamentRepository.findAllById(team.tournaments());

        Team newTeam = Team.builder()
                .name(team.name())
                .responsible(responsibleUser)
                .players(players)
                .tournaments(tournaments)
                .inserted_at(LocalDateTime.now())
                .build();

       return repository.save(newTeam);



    }

}
