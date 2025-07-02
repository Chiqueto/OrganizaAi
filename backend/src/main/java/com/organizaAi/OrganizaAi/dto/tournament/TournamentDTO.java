package com.organizaAi.OrganizaAi.dto.tournament;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.util.Date;

import com.organizaAi.OrganizaAi.enums.Category;
import com.organizaAi.OrganizaAi.enums.TournamentType;


public record TournamentDTO(
    @NotBlank(message = "Admin ID cannot be blank")
    String admin_id,
    @NotBlank(message = "Name cannot be blank")
    String name,
    @NotBlank(message = "CEP cannot be blank")
    @Pattern(regexp = "^[0-9]{5}-[0-9]{3}$", message = "CEP must be in the format XXXXX-XXX")
    String cep,
    @NotBlank(message = "City cannot be blank")
    String city,
    @NotBlank(message = "State cannot be blank")
    @Pattern(regexp = "^[A-Z]{2}$", message = "State must be a valid two-letter abbreviation")
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
    @NotNull(message = "Registration fee cannot be blank") 
    BigDecimal registration_fee, 
    @NotNull(message = "Registration deadline cannot be blank")
    @FutureOrPresent(message = "Registration deadline must be in the present or future")
    Date registration_deadline,
    @NotNull(message = "Latitude is required.")
    @Min(value = -90, message = "Latitude must be min -90.")
    @Max(value = 90, message = "Latitude must be max 90.")
    Double latitude,
    @NotNull(message = "Longitude is required.")
    @Min(value = -180, message = "Longitude must be min -180.")
    @Max(value = 180, message = "Longitude must be max 180.")
    Double longitude
) {
}