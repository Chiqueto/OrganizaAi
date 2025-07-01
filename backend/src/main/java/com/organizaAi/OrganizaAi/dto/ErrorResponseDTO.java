package com.organizaAi.OrganizaAi.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;
import java.util.Map;

public record ErrorResponseDTO (
    int status,
    Map<String, String> message
) {

}