package com.organizaAi.OrganizaAi.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;
// import jakarta.validation.constraints.CPF;
import jakarta.validation.constraints.Pattern;
import com.organizaAi.OrganizaAi.domain.Role;
import java.util.Set;


public record UserRegisterDTO (
    @NotNull
    @NotEmpty
    String name,
    @NotNull    
    @NotEmpty
    @Email
    String email,
    @NotNull
    @NotEmpty
    String password,
    @NotNull
    @NotEmpty
    //@CPF
    String cpf,
    @NotNull
    @NotEmpty
    String rg,
    @NotNull
    @NotEmpty
    @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "Phone must be in the format (XX) XXXXX-XXXX or (XX) XXXX-XXXX")
    String phone,
    @NotNull
    @NotEmpty
    @Pattern(regexp = "\\d{5}-\\d{3}|\\d{8}", message = "CEP must be in the format XXXXX-XXX or XXXXXX")
    String cep,
    @NotNull
    @NotEmpty
    String city,
    @NotNull
    @NotEmpty
    String state,
    @NotNull
    String birthDate,
    Set<Role> roles

    
    
    ) {
    
}