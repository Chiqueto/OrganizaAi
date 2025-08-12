package com.organizaAi.OrganizaAi.service.mapper;

import com.organizaAi.OrganizaAi.domain.Tournament;
import com.organizaAi.OrganizaAi.domain.User;
import com.organizaAi.OrganizaAi.domain.team.Team;
import com.organizaAi.OrganizaAi.dto.team.TeamResponse;
import com.organizaAi.OrganizaAi.dto.tournament.TournamentSummary;
import com.organizaAi.OrganizaAi.dto.user.PlayerSummary;
import com.organizaAi.OrganizaAi.dto.user.UserSummary;
import org.mapstruct.Mapper;


@Mapper (componentModel = "spring") // Para que o Spring possa injetá-la
public interface TeamMapper {


    TeamResponse toTeamResponse(Team team);


    UserSummary toUserSummary(User user);


    PlayerSummary toPlayerSummary(User user);


    TournamentSummary toTournamentSummary(Tournament tournament);

}
