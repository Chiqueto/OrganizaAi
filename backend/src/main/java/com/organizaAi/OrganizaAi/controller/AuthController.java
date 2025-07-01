package com.organizaAi.OrganizaAi.controller;

import com.organizaAi.OrganizaAi.dto.LoginDTO;
import com.organizaAi.OrganizaAi.domain.User;
import com.organizaAi.OrganizaAi.service.JwtService;
import com.organizaAi.OrganizaAi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.organizaAi.OrganizaAi.dto.UserRegisterDTO;
import com.organizaAi.OrganizaAi.dto.UserAuthenticatedDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints para autenticação e autorização")
public class AuthController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    @Operation(summary = "Registro de novo usuário", description = "Registra um novo usuário no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Dados inválidos"),
        @ApiResponse(responseCode = "409", description = "Usuário já existe"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public UserAuthenticatedDTO addNewUser(@RequestBody @Valid UserRegisterDTO user) {
        return userService.addUser(user);
    }

    @PostMapping("/login")
    @Operation(summary = "Login de usuário", description = "Autentica usuário e retorna token JWT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    public UserAuthenticatedDTO authenticateAndGetToken(@RequestBody LoginDTO loginDTO) {
        return userService.authenticateAndGetToken(loginDTO);
    }

}
