package com.example.Lab5AppDev.service;

import com.example.Lab5AppDev.dto.HouseholdDto;
import com.example.Lab5AppDev.dto.HouseholdStatisticsDto;
import com.example.Lab5AppDev.entity.Household;
import com.example.Lab5AppDev.exception.HouseholdNotFoundException;
import com.example.Lab5AppDev.repository.HouseholdRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseholdServiceImpl implements HouseholdService {

    private final HouseholdRepository householdRepository;

    // Autowiring is done here implicitly
    public HouseholdServiceImpl(HouseholdRepository householdRepository) {
        this.householdRepository = householdRepository;
    }

    @Override
    public Household createHousehold(Household household) {
        return householdRepository.save(household);
    }

    @Override
    public List<Household> getAllHouseholds() {
        return householdRepository.findAll();
    }

    @Override
    public Household getHouseholdById(String eircode) {
        return householdRepository.findById(eircode)
                .orElseThrow(() -> new HouseholdNotFoundException("Household not found with Eircode: " + eircode));
    }

    @Override
    public Household getHouseholdByIdWithPets(String eircode) {
        return householdRepository.findByEircodeWithPets(eircode);
    }

    @Override
    public HouseholdStatisticsDto getHouseholdStatistics() {
        return householdRepository.getHouseholdStatistics();
    }

    @Override
    public Household updateHousehold(String eircode, Household updatedDetails) {
        Household household = getHouseholdById(eircode);
        household.setNumberOfOccupants(updatedDetails.getNumberOfOccupants());
        household.setMaxNumberOfOccupants(updatedDetails.getMaxNumberOfOccupants());
        household.setOwnerOccupied(updatedDetails.isOwnerOccupied());
        return householdRepository.save(household);
    }

    @Override
    public void deleteHousehold(String eircode) {
        householdRepository.deleteById(eircode);
    }

    @Override
    public List<Household> findHouseholdsWithNoPets() {
        return householdRepository.findHouseholdsWithNoPets();
    }

    @Override
    public List<Household> findOwnerOccupiedHouseholds() {
        return householdRepository.findByOwnerOccupiedTrue();
    }
}
