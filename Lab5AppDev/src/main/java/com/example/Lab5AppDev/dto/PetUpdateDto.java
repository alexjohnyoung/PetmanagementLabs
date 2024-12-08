package com.example.Lab5AppDev.dto;

import jakarta.validation.constraints.NotBlank;

public record PetUpdateDto(
        @NotBlank String name // Validates that the name cannot be blank
) {
}