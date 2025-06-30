package com.organizaAi.OrganizaAi.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;

public record LoginDTO (
    @NotNull    
    @NotEmpty
    @Email
    String email,
    @NotNull
    @NotEmpty
    String password) {

}