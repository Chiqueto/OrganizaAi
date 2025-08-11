package com.organizaAi.OrganizaAi.dto.commom;

import java.util.Optional;

public record Pagination(
        int currentPage,
        int pageSize,
        Optional<String> nextPage,
        Optional<String> previousPage,
        int total
) {
}
