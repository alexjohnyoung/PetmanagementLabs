package com.example.Lab5AppDev.controller;

import com.example.Lab5AppDev.dto.HouseholdDto;
import com.example.Lab5AppDev.dto.HouseholdStatisticsDto;
import com.example.Lab5AppDev.dto.PetDto;
import com.example.Lab5AppDev.entity.Household;
import com.example.Lab5AppDev.entity.Pet;
import com.example.Lab5AppDev.service.HouseholdService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/households")
public class HouseholdController {

    private final HouseholdService householdService;

    public HouseholdController(HouseholdService householdService) {
        this.householdService = householdService;
    }

    @GetMapping
    public ResponseEntity<List<HouseholdDto>> getAllHouseholds() {
        List<Household> households = householdService.getAllHouseholds();
        List<HouseholdDto> dtos = households.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{eircode}")
    public ResponseEntity<HouseholdDto> getHouseholdById(@PathVariable String eircode) {
        Household household = householdService.getHouseholdById(eircode);
        return ResponseEntity.ok(toDto(household));
    }

    @GetMapping("/no-pets")
    public ResponseEntity<List<Household>> getHouseholdsWithNoPets() {
        return ResponseEntity.ok(householdService.findHouseholdsWithNoPets());
    }

    @PostMapping
    public ResponseEntity<Household> createHousehold(@Valid @RequestBody Household household) {
        Household createdHousehold = householdService.createHousehold(household);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{eircode}")
                .buildAndExpand(createdHousehold.getEircode())
                .toUri();
        return ResponseEntity.created(location).body(createdHousehold);
    }

    @DeleteMapping("/{eircode}")
    public ResponseEntity<Void> deleteHousehold(@PathVariable String eircode) {
        householdService.deleteHousehold(eircode);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statistics")
    public ResponseEntity<HouseholdStatisticsDto> getHouseholdStatistics() {
        return ResponseEntity.ok(householdService.getHouseholdStatistics());
    }

    private HouseholdDto toDto(Household household) {
        return new HouseholdDto(
                household.getEircode(),
                household.getNumberOfOccupants(),
                household.getMaxNumberOfOccupants(),
                household.isOwnerOccupied(),
                household.getPets().stream().map(this::toPetDto).collect(Collectors.toList())
        );
    }

    private PetDto toPetDto(Pet pet) {
        return new PetDto(
                pet.getId(),
                pet.getName(),
                pet.getAnimalType(),
                pet.getBreed(),
                pet.getAge(),
                pet.getHousehold().getEircode()
        );
    }
}
