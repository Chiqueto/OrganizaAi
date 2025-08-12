package com.organizaAi.OrganizaAi.service.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IdMapper {
    default String map(java.util.UUID id) {
        return id != null ? id.toString() : null;
    }
}