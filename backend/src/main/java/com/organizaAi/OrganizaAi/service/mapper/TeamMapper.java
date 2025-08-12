package com.organizaAi.OrganizaAi.service.mapper;

import com.organizaAi.OrganizaAi.domain.Tournament;
import com.organizaAi.OrganizaAi.domain.User;
import com.organizaAi.OrganizaAi.domain.team.Team;
import com.organizaAi.OrganizaAi.dto.team.TeamResponse;
import com.organizaAi.OrganizaAi.dto.tournament.TournamentSummary;
import com.organizaAi.OrganizaAi.dto.user.PlayerSummary;
import com.organizaAi.OrganizaAi.dto.user.UserSummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Collection;
import java.util.List;


@Mapper(
        config = CentralMapperConfig.class,
        uses = { UserMapper.class, PlayerMapper.class, TournamentMapper.class }
)
public interface TeamMapper {
    @Mappings({
            @Mapping(target = "id",          source = "id"),          // UUID -> String via IdMapper
            @Mapping(target = "name",        source = "name"),
            @Mapping(target = "responsible", source = "responsible"), // User -> UserSummary via UserMapper
            @Mapping(target = "players",     source = "players"),     // List<User> -> List<PlayerSummary> via PlayerMapper
            @Mapping(target = "tournaments", source = "tournaments")  // List<Tournament> -> List<TournamentSummary>
    })
    TeamResponse toTeamResponse(Team team);
}
