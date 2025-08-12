package com.organizaAi.OrganizaAi.dto.commom;

import java.util.Optional;

public record Pagination(
        int page,
        int size,
        long totalElements,
        int totalPages,
        String sort,
        boolean first,
        boolean last,
        boolean hasNext,
        boolean hasPrevious
) {
}
