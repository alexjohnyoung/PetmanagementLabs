package com.example.Lab5AppDev.dto;

import java.util.List;

public record HouseholdWithPetsDto(
        String eircode,
        Integer numberOfOccupants,
        Integer maxNumberOfOccupants,
        Boolean ownerOccupied,
        List<PetDto> pets // List of pets belonging to this household
) {
}
