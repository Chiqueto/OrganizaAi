package com.organizaAi.OrganizaAi.dto.tournament;

public record GetTournamentDTO (
    String id,
    String name,
    String description,
    String image,
    String type,
    String category,
    String admin_id,
    String admin_name,
    String cep,
    String city,
    String state,
    String street,
    String number,
    String district,
    Double latitude,
    Double longitude,
    String rules,
    String prizes,
    String registration_fee,
    String registration_deadline,
    String start_date,
    String end_date,
    Boolean active
){
    
}
