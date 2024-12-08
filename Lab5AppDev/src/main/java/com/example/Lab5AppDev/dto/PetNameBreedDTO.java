package com.example.Lab5AppDev.dto;

import jakarta.validation.constraints.NotBlank;

public record PetNameBreedDTO(
        @NotBlank String name,
        @NotBlank String breed) {}
