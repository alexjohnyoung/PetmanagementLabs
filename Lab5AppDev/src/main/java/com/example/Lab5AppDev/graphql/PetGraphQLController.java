package com.example.Lab5AppDev.graphql;

import com.example.Lab5AppDev.dto.PetDto;
import com.example.Lab5AppDev.dto.PetInput;
import com.example.Lab5AppDev.dto.PetStatisticsDto;
import com.example.Lab5AppDev.entity.Pet;
import com.example.Lab5AppDev.service.PetService;
import com.example.Lab5AppDev.service.HouseholdService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PetGraphQLController {

    private final PetService petService;
    private final HouseholdService householdService;

    public PetGraphQLController(PetService petService, HouseholdService householdService) {
        this.petService = petService;
        this.householdService = householdService;
    }

    @QueryMapping
    public List<PetDto> getAllPetsByAnimalType(@Argument String animalType) {
        List<Pet> pets = petService.findPetsByAnimalType(animalType);
        return pets.stream().map(this::toDto).collect(Collectors.toList());
    }

    @QueryMapping
    public PetDto getPet(@Argument Long id) {
        Pet pet = petService.getPetById(id);
        return toDto(pet);
    }

    @QueryMapping
    public PetStatisticsDto getPetStatistics() {
        return petService.getPetStatistics();
    }

    @MutationMapping
    public PetDto createPet(@Argument PetInput petInput) {
        Pet pet = toEntity(petInput); // Convert PetInput to Pet entity
        Pet savedPet = petService.createPet(pet);
        return toDto(savedPet); // Convert saved entity to PetDto
    }

    @MutationMapping
    public String deletePet(@Argument Long id) {
        petService.deletePetById(id);
        return "Pet deleted successfully.";
    }

    @MutationMapping
    public String deletePetsByName(@Argument String name) {
        petService.deletePetsByName(name);
        return "Pets with name '" + name + "' deleted successfully.";
    }

    private PetDto toDto(Pet pet) {
        return new PetDto(
                pet.getId(),
                pet.getName(),
                pet.getAnimalType(),
                pet.getBreed(),
                pet.getAge(),
                pet.getHousehold().getEircode()
        );
    }

    private Pet toEntity(PetInput input) {
        Pet pet = new Pet();
        pet.setName(input.name());
        pet.setAnimalType(input.animalType());
        pet.setBreed(input.breed());
        pet.setAge(input.age());
        pet.setHousehold(householdService.getHouseholdById(input.householdEircode())); // Using householdService here
        return pet;
    }
}
