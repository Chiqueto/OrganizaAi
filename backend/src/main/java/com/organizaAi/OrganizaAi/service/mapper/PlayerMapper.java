package com.organizaAi.OrganizaAi.service.mapper;

import com.organizaAi.OrganizaAi.domain.User;
import com.organizaAi.OrganizaAi.dto.user.PlayerSummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = IdMapper.class)
public interface PlayerMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    PlayerSummary toSummary(User player);

    List<PlayerSummary> toSummaryList(Collection<User> players);
}