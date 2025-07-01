package com.organizaAi.OrganizaAi.service;

import com.organizaAi.OrganizaAi.repository.UserRepository;
import com.organizaAi.OrganizaAi.domain.Role;
import com.organizaAi.OrganizaAi.domain.Tournament;
import com.organizaAi.OrganizaAi.domain.User;
import com.organizaAi.OrganizaAi.dto.tournament.TournamentDTO;
import com.organizaAi.OrganizaAi.dto.commom.CreatedResponseDTO;
import com.organizaAi.OrganizaAi.infra.exceptions.NotFoundException;
import com.organizaAi.OrganizaAi.infra.exceptions.UnauthorizedException;
import com.organizaAi.OrganizaAi.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;

    public CreatedResponseDTO createTournament(TournamentDTO tournamentDTO) {

        User user = userRepository.findById(tournamentDTO.admin_id())
                .orElseThrow(() -> new NotFoundException("user", "User not found"));

        if (!user.getRoles().stream().anyMatch(role -> role.getRole().equals(Role.ORGANIZER))) {
            throw new UnauthorizedException("You do not have permission to create a tournament");
        }

        Tournament tournament = Tournament.builder()
                .name(tournamentDTO.name())
                .description(tournamentDTO.description())
                .start_date(tournamentDTO.start_date())
                .end_date(tournamentDTO.end_date())
                .admin(user)
                .cep(tournamentDTO.cep())
                .city(tournamentDTO.city())
                .state(tournamentDTO.state())
                .street(tournamentDTO.street())
                .number(tournamentDTO.number())
                .district(tournamentDTO.district())
                .image(tournamentDTO.image())
                .type(tournamentDTO.type())
                .category(tournamentDTO.category())
                .rules(tournamentDTO.rules())
                .prizes(tournamentDTO.prizes())
                .registration_fee(tournamentDTO.registration_fee())
                .registration_deadline(tournamentDTO.registration_deadline())
                .active(true)
                .inserted_at(new Date(System.currentTimeMillis()))
                .updated_at(new Date(System.currentTimeMillis()))
                .build();
        tournamentRepository.save(tournament);

        return new CreatedResponseDTO(201, "Torneio registrado com sucesso");
    }
}