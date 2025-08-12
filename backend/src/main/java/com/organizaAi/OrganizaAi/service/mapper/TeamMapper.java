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
        componentModel = "spring",
        uses = {
                TeamMapper.UserMapper.class,
                TeamMapper.PlayerMapper.class,
                TeamMapper.TournamentMapper.class
        }
)
public interface TeamMapper {
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "responsible", source = "responsible"),
            @Mapping(target = "players", source = "players"),
            @Mapping(target = "tournaments", source = "tournaments")
    })
    TeamResponse toTeamResponse(Team team);

    @Mapper(componentModel = "spring")
    interface UserMapper {
        @Mappings({
                @Mapping(target = "id", expression = "java(user.getId() != null ? user.getId().toString() : null)"),
                @Mapping(target = "name", source = "name"),
                @Mapping(target = "email", source = "email")
        })
        UserSummary toSummary(User user);
    }

    @Mapper(componentModel = "spring")
    interface PlayerMapper {
        @Mappings({
                @Mapping(target = "id", expression = "java(player.getId() != null ? player.getId().toString() : null)"),
                @Mapping(target = "name", source = "name")
        })
        PlayerSummary toSummary(User player);
        List<PlayerSummary> toSummaryList(Collection<User> players);
    }

    @Mapper(componentModel = "spring")
    interface TournamentMapper {
        @Mappings({
                @Mapping(target = "id", expression = "java(tournament.getId() != null ? tournament.getId().toString() : null)"),
                @Mapping(target = "name", source = "name")
        })
        TournamentSummary toSummary(Tournament tournament);

        List<TournamentSummary> toSummaryList(Collection<Tournament> tournaments);
    }
}
