package com.organizaAi.OrganizaAi.service.mapper;

import com.organizaAi.OrganizaAi.domain.User;
import com.organizaAi.OrganizaAi.dto.user.UserSummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = IdMapper.class)
public interface UserMapper {
    @Mapping(target = "id", source = "id")  // UUID -> String via IdMapper
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    UserSummary toSummary(User user);
}
