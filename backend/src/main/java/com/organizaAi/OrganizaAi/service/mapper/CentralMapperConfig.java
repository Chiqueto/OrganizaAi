package com.organizaAi.OrganizaAi.service.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = { IdMapper.class }
)
public interface CentralMapperConfig {}