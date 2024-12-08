package com.example.Lab5AppDev.service;

import com.example.Lab5AppDev.dto.HouseholdDto;
import com.example.Lab5AppDev.dto.HouseholdStatisticsDto;
import com.example.Lab5AppDev.dto.HouseholdWithPetsDto;
import com.example.Lab5AppDev.entity.Household;

import java.util.List;

public interface HouseholdService {
    Household createHousehold(Household household);

    List<Household> getAllHouseholds();

    Household getHouseholdById(String eircode);

    HouseholdStatisticsDto getHouseholdStatistics();

    Household getHouseholdByIdWithPets(String eircode);

    Household updateHousehold(String eircode, Household updatedDetails);

    void deleteHousehold(String eircode);

    List<Household> findHouseholdsWithNoPets();

    List<Household> findOwnerOccupiedHouseholds();
}
