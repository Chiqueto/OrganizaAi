package com.organizaAi.OrganizaAi.controller.team;

import com.organizaAi.OrganizaAi.domain.team.Team;
import com.organizaAi.OrganizaAi.dto.commom.ApiError;
import com.organizaAi.OrganizaAi.dto.commom.ApiMeta;
import com.organizaAi.OrganizaAi.dto.commom.ApiResponse;
import com.organizaAi.OrganizaAi.dto.commom.Pagination;
import com.organizaAi.OrganizaAi.dto.team.TeamDTO;
import com.organizaAi.OrganizaAi.dto.team.TeamResponse;
import com.organizaAi.OrganizaAi.infra.exceptions.NotFoundException;
import com.organizaAi.OrganizaAi.service.team.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("team")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService service;
    @PostMapping("")
    @Operation(summary = "Registro de novo time", description = "Registra um novo time no sistema")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Time registrado com sucesso"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acesso negado - usuário não é um atleta ou coach"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Conflito de dados/violação de integridade"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<ApiResponse<TeamResponse>> createTeam(@RequestBody @Valid TeamDTO teamDTO) {
        TeamResponse team = service.createTeam(teamDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, team, null, null, "Time cadastrado com sucesso!"));
    }

    @GetMapping("/{responsibleId}")
    public ResponseEntity<ApiResponse<List<TeamResponse>>> list(
            @RequestParam String responsibleId,
            @PageableDefault(size = 20, sort = "name") Pageable pageable
    ) {
        Page<TeamResponse> page = service.getTeamsByResponsable(responsibleId, pageable);

        ApiMeta meta = new ApiMeta(new Pagination(
                page.getNumber(),           // página atual (0-based)
                page.getSize(),             // tamanho
                page.getTotalElements(),    // total de registros
                page.getTotalPages(),       // total de páginas
                page.getSort().toString(),  // ordenação
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
            )
        );

        ApiResponse<List<TeamResponse>> body = new ApiResponse<>(
                true,
                page.getContent(), // só a lista da página
                null,
                meta,
                "Lista de times"
        );

        return ResponseEntity.ok(body);
    }
}
