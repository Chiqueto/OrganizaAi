package com.organizaAi.OrganizaAi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.organizaAi.OrganizaAi.dto.tournament.TournamentDTO;
import com.organizaAi.OrganizaAi.dto.commom.CreatedResponseDTO;
import com.organizaAi.OrganizaAi.service.TournamentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
        @ApiResponse(responseCode = "409", description = "Torneio já existe"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public CreatedResponseDTO createTournament(@RequestBody @Valid TournamentDTO tournamentDTO) {
        return tournamentService.createTournament(tournamentDTO);
    }
}