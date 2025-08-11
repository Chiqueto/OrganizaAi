package com.organizaAi.OrganizaAi.dto.commom;

import java.util.Optional;

public record ApiResponse<T>(
        boolean success,
        T data,
        ApiError error,
        ApiMeta meta,
        String message
) {
}