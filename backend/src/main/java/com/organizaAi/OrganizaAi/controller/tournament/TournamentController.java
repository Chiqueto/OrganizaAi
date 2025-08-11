package com.organizaAi.OrganizaAi.controller.tournament;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.organizaAi.OrganizaAi.dto.tournament.NearbyTournamentsResponseDTO;
import com.organizaAi.OrganizaAi.dto.tournament.TournamentDTO;
import com.organizaAi.OrganizaAi.dto.commom.CreatedResponseDTO;
import com.organizaAi.OrganizaAi.service.TournamentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/tournament")
@Tag(name = "Tournament", description = "Endpoints para gerenciamento de torneios")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentService tournamentService;

    @PreAuthorize("hasRole('ORGANIZER')")
    @PostMapping("/")
    @Operation(summary = "Registro de novo torneio", description = "Registra um novo torneio no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Torneio registrado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - usuário não é um organizador"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
        @ApiResponse(responseCode = "409", description = "Torneio já existe"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<CreatedResponseDTO> createTournament(@RequestBody @Valid TournamentDTO tournamentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tournamentService.createTournament(tournamentDTO));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/next")
    @Operation(summary = "Busca torneios próximos ao usuário", description = "Busca torneios próximos ao usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - Token inválido ou expirado"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<NearbyTournamentsResponseDTO> getTournamentsNext(@RequestParam double latitude,
                                                                @RequestParam double longitude,
                                                                @RequestParam double radius) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(tournamentService.getTournamentsNext(latitude, longitude, radius));
    }
}