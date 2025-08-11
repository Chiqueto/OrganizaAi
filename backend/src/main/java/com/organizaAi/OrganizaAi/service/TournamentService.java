package com.organizaAi.OrganizaAi.service;

import com.organizaAi.OrganizaAi.repository.UserRepository;
import com.organizaAi.OrganizaAi.domain.Role;
import com.organizaAi.OrganizaAi.domain.Tournament;
import com.organizaAi.OrganizaAi.domain.User;
import com.organizaAi.OrganizaAi.dto.tournament.GetTournamentDTO;
import com.organizaAi.OrganizaAi.dto.tournament.NearbyTournamentsResponseDTO;
import com.organizaAi.OrganizaAi.dto.tournament.TournamentDTO;
import com.organizaAi.OrganizaAi.dto.commom.CreatedResponseDTO;
import com.organizaAi.OrganizaAi.infra.exceptions.NotFoundException;
import com.organizaAi.OrganizaAi.infra.exceptions.UnauthorizedException;
import com.organizaAi.OrganizaAi.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

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

        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

        Point locationPoint = geometryFactory.createPoint(
                new Coordinate(tournamentDTO.longitude(), tournamentDTO.latitude())
        );

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
                .inserted_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .location(locationPoint)
                .build();
        tournamentRepository.save(tournament);

        return new CreatedResponseDTO(201, "Torneio registrado com sucesso");
    }

    public NearbyTournamentsResponseDTO getTournamentsNext(double latitude, double longitude, double radius) {

        List<Tournament> tournaments = tournamentRepository.findTournamentsNearby(longitude, latitude, radius);
        
        List<GetTournamentDTO> tournamentDTOs = tournaments.stream()
                .map(this::convertToGetTournamentDTO)
                .toList();

        String message = tournaments.isEmpty() ? "Nenhum torneio encontrado nas proximidades" : "Torneios encontrados com sucesso";

        return new NearbyTournamentsResponseDTO(200, message, tournamentDTOs);
    }

    private GetTournamentDTO convertToGetTournamentDTO(Tournament tournament) {
        return new GetTournamentDTO(
                tournament.getId(),
                tournament.getName(),
                tournament.getDescription(),
                tournament.getImage(),
                tournament.getType().name(),
                tournament.getCategory().name(),
                tournament.getAdmin().getId(),
                tournament.getAdmin().getName(),
                tournament.getCep(),
                tournament.getCity(),
                tournament.getState(),
                tournament.getStreet(),
                tournament.getNumber(),
                tournament.getDistrict(),
                tournament.getLocation().getY(), // Latitude
                tournament.getLocation().getX(), // Longitude
                tournament.getRules(),
                tournament.getPrizes(),
                String.valueOf(tournament.getRegistration_fee()),
                String.valueOf(tournament.getRegistration_deadline()),
                String.valueOf(tournament.getStart_date()),
                String.valueOf(tournament.getEnd_date()),
                tournament.getActive());
    }
}