package com.example.Lab5AppDev.repository;

import com.example.Lab5AppDev.dto.HouseholdDto;
import com.example.Lab5AppDev.dto.HouseholdStatisticsDto;
import com.example.Lab5AppDev.dto.HouseholdWithPetsDto;
import com.example.Lab5AppDev.entity.Household;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HouseholdRepository extends JpaRepository<Household, String> {

    // Find household by Eircode with pets (eager fetching)
    @Query("SELECT h FROM Household h LEFT JOIN FETCH h.pets WHERE h.eircode = :eircode")
    Household findByEircodeWithPets(String eircode);

    // Find households with no pets
    @Query("SELECT h FROM Household h WHERE h.pets IS EMPTY")
    List<Household> findHouseholdsWithNoPets();

    @Query("SELECT " +
            "new com.example.Lab5AppDev.dto.HouseholdStatisticsDto(" +
            "SUM(CASE WHEN h.numberOfOccupants = 0 THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN h.numberOfOccupants = h.maxNumberOfOccupants THEN 1 ELSE 0 END)) " +
            "FROM Household h")
    HouseholdStatisticsDto getHouseholdStatistics();

    // Find owner-occupied households
    List<Household> findByOwnerOccupiedTrue();
}
