package com.example.Lab5AppDev.service;
import com.example.Lab5AppDev.exception.PetNotFoundException;
import com.example.Lab5AppDev.repository.PetRepository;
import org.springframework.stereotype.Service;
import com.example.Lab5AppDev.entity.Pet;
import com.example.Lab5AppDev.dto.PetNameBreedDTO;
import com.example.Lab5AppDev.dto.PetStatisticsDto;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Pet createPet(Pet pet) {
        if (pet.getName() == null || pet.getAnimalType() == null || pet.getBreed() == null || pet.getAge() <= 0) {
            throw new IllegalArgumentException("Invalid pet data provided.");
        }
        return petRepository.save(pet);
    }

    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public Pet getPetById(Long id) {

        return petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException("Pet not found with ID: " + id));
    }

    @Override
    public Pet changePetName(Long id, String newName) {
        Pet pet = getPetById(id);
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank.");
        }
        pet.setName(newName);
        return petRepository.save(pet);
    }

    @Override
    public Pet updatePet(Long id, Pet petDetails) {
        Pet existingPet = getPetById(id);
        existingPet.setName(petDetails.getName());
        existingPet.setAnimalType(petDetails.getAnimalType());
        existingPet.setBreed(petDetails.getBreed());
        existingPet.setAge(petDetails.getAge());
        return petRepository.save(existingPet);
    }

    @Override
    public void deletePetById(Long id) {
        Pet pet = getPetById(id);
        petRepository.delete(pet);
    }

    @Override
    public void deletePetsByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        petRepository.deleteByNameIgnoreCase(name);
    }

    @Override
    public List<Pet> findPetsByAnimalType(String animalType) {
        return petRepository.findByAnimalTypeIgnoreCase(animalType);
    }

    @Override
    public List<Pet> findPetsByBreed(String breed) {
        return petRepository.findByBreedOrderByAgeAsc(breed);
    }

    @Override
    public List<PetNameBreedDTO> getNameAndBreed() {
        return petRepository.findNameAndBreed();
    }

    @Override
    public PetStatisticsDto getPetStatistics() {
        return petRepository.getPetStatistics();
    }
}
