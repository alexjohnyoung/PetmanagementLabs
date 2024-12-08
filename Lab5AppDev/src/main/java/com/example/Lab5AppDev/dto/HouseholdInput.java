package com.example.Lab5AppDev.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record HouseholdInput(
        @NotBlank String eircode, // Eircode must not be blank
        @NotNull Integer numberOfOccupants, // Number of occupants cannot be null
        @NotNull Integer maxNumberOfOccupants, // Max occupants cannot be null
        @NotNull Boolean ownerOccupied, // Boolean field must be provided
        List<PetInput> pets
) {
}
