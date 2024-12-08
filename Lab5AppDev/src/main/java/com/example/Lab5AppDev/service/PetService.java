package com.example.Lab5AppDev.service;

import com.example.Lab5AppDev.dto.PetNameBreedDTO;
import com.example.Lab5AppDev.dto.PetStatisticsDto;
import com.example.Lab5AppDev.entity.Pet;

import java.util.List;

public interface PetService {
    Pet createPet(Pet pet);

    List<Pet> getAllPets();

    Pet getPetById(Long id);

    Pet changePetName(Long id, String newName);

    Pet updatePet(Long id, Pet petDetails);

    void deletePetById(Long id);

    void deletePetsByName(String name);

    List<Pet> findPetsByAnimalType(String animalType);

    List<Pet> findPetsByBreed(String breed);

    List<PetNameBreedDTO> getNameAndBreed();

    PetStatisticsDto getPetStatistics();
}
