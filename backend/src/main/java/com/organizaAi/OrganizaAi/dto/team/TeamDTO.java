package com.organizaAi.OrganizaAi.dto.team;

import com.organizaAi.OrganizaAi.domain.Tournament;
import com.organizaAi.OrganizaAi.domain.User;

import java.util.List;

public record TeamDTO(
        String name,
        String responsible_id,
        List<String> players,
        List<String> tournaments
) {
}
