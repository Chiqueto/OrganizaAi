package com.organizaAi.OrganizaAi.dto.commom;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;
import java.util.Map;

public record CreatedResponseDTO (
    int status,
    String message
) {

}