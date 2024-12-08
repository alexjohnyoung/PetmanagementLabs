package com.example.Lab5AppDev.graphql;

import com.example.Lab5AppDev.dto.HouseholdDto;
import com.example.Lab5AppDev.dto.HouseholdInput;
import com.example.Lab5AppDev.dto.HouseholdStatisticsDto;
import com.example.Lab5AppDev.dto.PetDto;
import com.example.Lab5AppDev.entity.Household;
import com.example.Lab5AppDev.entity.Pet;
import com.example.Lab5AppDev.service.HouseholdService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HouseholdGraphQLController {

    private final HouseholdService householdService;

    public HouseholdGraphQLController(HouseholdService householdService) {
        this.householdService = householdService;
    }

    @QueryMapping
    public List<HouseholdDto> getAllHouseholds() {
        List<Household> households = householdService.getAllHouseholds();
        return households.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @QueryMapping
    public HouseholdDto getHousehold(@Argument String eircode) {
        Household household = householdService.getHouseholdById(eircode);
        return toDto(household);
    }

    @MutationMapping
    public HouseholdDto createHousehold(@Argument("household") HouseholdInput householdInput) {
        // Convert PetInput to PetDto
        List<PetDto> petDtos = householdInput.pets().stream()
                .map(petInput -> new PetDto(
                        null, // ID is null for new pets
                        petInput.name(),
                        petInput.animalType(),
                        petInput.breed(),
                        petInput.age(),
                        householdInput.eircode() // Assign the eircode
                ))
                .collect(Collectors.toList());

        HouseholdDto householdDto = new HouseholdDto(
                householdInput.eircode(),
                householdInput.numberOfOccupants(),
                householdInput.maxNumberOfOccupants(),
                householdInput.ownerOccupied(),
                petDtos
        );

        // Save the Household entity
        Household household = toEntity(householdDto);
        Household savedHousehold = householdService.createHousehold(household);
        return toDto(savedHousehold);
    }

    @QueryMapping
    public List<HouseholdDto> findHouseholdsWithNoPets() {
        List<Household> households = householdService.findHouseholdsWithNoPets();
        return households.stream().map(this::toDto).collect(Collectors.toList());
    }

    @QueryMapping
    public List<HouseholdDto> findOwnerOccupiedHouseholds() {
        List<Household> households = householdService.findOwnerOccupiedHouseholds();
        return households.stream().map(this::toDto).collect(Collectors.toList());
    }

    @QueryMapping
    public HouseholdStatisticsDto getHouseholdStatistics() {
        return householdService.getHouseholdStatistics();
    }

    @MutationMapping
    public String deleteHousehold(@Argument String eircode) {
        householdService.deleteHousehold(eircode);
        return "Household deleted successfully.";
    }

    // Converts Household entity to HouseholdDto
    private HouseholdDto toDto(Household household) {
        List<PetDto> petDtos = household.getPets().stream()
                .map(pet -> new PetDto(
                        pet.getId(),
                        pet.getName(),
                        pet.getAnimalType(),
                        pet.getBreed(),
                        pet.getAge(),
                        household.getEircode()
                ))
                .collect(Collectors.toList());

        return new HouseholdDto(
                household.getEircode(),
                household.getNumberOfOccupants(),
                household.getMaxNumberOfOccupants(),
                household.isOwnerOccupied(),
                petDtos
        );
    }

    // Convert HouseholdDto to Household entity
    private Household toEntity(HouseholdDto householdDto) {
        Household household = new Household();
        household.setEircode(householdDto.eircode());
        household.setNumberOfOccupants(householdDto.numberOfOccupants());
        household.setMaxNumberOfOccupants(householdDto.maxNumberOfOccupants());
        household.setOwnerOccupied(householdDto.ownerOccupied());

        List<Pet> pets = householdDto.pets().stream()
                .map(petDto -> {
                    Pet pet = new Pet();
                    pet.setName(petDto.name());
                    pet.setAnimalType(petDto.animalType());
                    pet.setBreed(petDto.breed());
                    pet.setAge(petDto.age());
                    pet.setHousehold(household); // Set relationship
                    return pet;
                })
                .collect(Collectors.toList());
        household.setPets(pets);

        return household;
    }

}
