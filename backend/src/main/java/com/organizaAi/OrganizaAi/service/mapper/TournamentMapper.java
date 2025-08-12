package com.organizaAi.OrganizaAi.service.mapper;

import com.organizaAi.OrganizaAi.domain.Tournament;
import com.organizaAi.OrganizaAi.dto.tournament.TournamentSummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = IdMapper.class)
public interface TournamentMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    TournamentSummary toSummary(Tournament tournament);

    List<TournamentSummary> toSummaryList(Collection<Tournament> tournaments);
}