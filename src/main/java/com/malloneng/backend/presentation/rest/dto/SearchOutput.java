package com.malloneng.backend.presentation.rest.dto;

import com.malloneng.backend.domain.value.Id;

import java.util.Set;

public record SearchOutput(
        Id id,
        Set<String> urls,
        String status
) {
}
