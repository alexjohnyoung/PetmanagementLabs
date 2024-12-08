package com.example.Lab5AppDev.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PetInput(
        @NotBlank String name,
        @NotBlank String animalType,
        @NotBlank String breed,
        @NotNull Integer age,
        @NotBlank String householdEircode
) {}