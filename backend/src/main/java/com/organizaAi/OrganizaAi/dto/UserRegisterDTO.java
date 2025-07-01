package com.organizaAi.OrganizaAi.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import com.organizaAi.OrganizaAi.domain.Role;

import java.sql.Date;
import java.util.Set;


public record UserRegisterDTO (
    @NotBlank(message = "Name should not be blank")
    String name,

    @NotBlank(message = "Email should not be blank")
    @Email(message = "Email should be valid")
    String email,

    @NotBlank(message = "Password should not be blank")
    String password,

    @CPF(message = "CPF should be valid")
    @NotBlank(message = "CPF should not be blank")
    String cpf,

    @NotBlank(message = "RG should not be blank")
    @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}-\\d{1}|\\d{2}\\d{3}\\d{3}-\\d{1}", message = "RG must be in the format XX.XXX.XXX-X or XXXXXXXXXX-X")
    String rg,

    @NotBlank(message = "Phone should not be blank")
    @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "Phone must be in the format (XX) XXXXX-XXXX or (XX) XXXX-XXXX")
    String phone,

    @NotBlank(message = "CEP should not be blank")
    @Pattern(regexp = "\\d{5}-\\d{3}|\\d{8}", message = "CEP must be in the format XXXXX-XXX or XXXXXX")
    String cep,

    @NotBlank(message = "City should not be blank")
    String city,

    @NotBlank(message = "State should not be blank")
    @Pattern(regexp = "^[A-Z]{2}$", message = "State must be a valid two-letter abbreviation")
    String state,

    @NotNull(message = "Birth date should not be null")
    Date birthDate,

    @NotNull(message = "Roles should not be null")
    Set<Role> roles

) {}