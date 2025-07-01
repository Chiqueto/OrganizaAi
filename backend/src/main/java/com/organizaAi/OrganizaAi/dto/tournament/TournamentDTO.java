package com.organizaAi.OrganizaAi.dto.tournament;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import java.util.Date;

import com.organizaAi.OrganizaAi.enums.Category;
import com.organizaAi.OrganizaAi.enums.TournamentType;


public record TournamentDTO(
    @NotBlank(message = "Admin ID cannot be blank")
    String admin_id,
    @NotBlank(message = "Name cannot be blank")
    String name,
    @NotBlank(message = "CEP cannot be blank")
    String cep,
    @NotBlank(message = "City cannot be blank")
    String city,
    @NotBlank(message = "State cannot be blank")
    String state,
    @NotBlank(message = "Street cannot be blank")
    String street,
    @NotBlank(message = "Number cannot be blank")
    String number,
    String district,
    @NotNull(message = "Start date should not be null")
    @FutureOrPresent(message = "Start date must be in the present or future")
    Date start_date, 
    @NotNull(message = "End date should not be null")
    @Future(message = "End date must be in the future")
    Date end_date, 
    String description,
    String image,
    @NotNull(message = "Type cannot be blank")
    TournamentType type,
    @NotNull(message = "Category cannot be blank")
    Category category, 
    String rules, 
    String prizes,
    @NotBlank(message = "Registration fee cannot be blank") 
    String registration_fee, 
    @NotNull(message = "Registration deadline cannot be blank")
    @FutureOrPresent(message = "Registration deadline must be in the present or future")
    Date registration_deadline
) {
}