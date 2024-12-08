package com.example.Lab5AppDev.repository;

import com.example.Lab5AppDev.entity.Pet;
import com.example.Lab5AppDev.dto.PetNameBreedDTO;
import com.example.Lab5AppDev.dto.PetStatisticsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    // Find pets by animal type (case-insensitive)
    List<Pet> findByAnimalTypeIgnoreCase(String animalType);

    // Find pets by breed, ordered by age ascending
    List<Pet> findByBreedOrderByAgeAsc(String breed);

    // Delete pets by name (case-insensitive)
    @Transactional
    void deleteByNameIgnoreCase(String name);

    // Projection query to fetch name, animal type, and breed
    @Query("SELECT new com.example.Lab5AppDev.dto.PetNameBreedDTO(p.name, p.breed) FROM Pet p")
    List<PetNameBreedDTO> findNameAndBreed();

    // Query for pet statistics (average and max age)
    @Query("SELECT new com.example.Lab5AppDev.dto.PetStatisticsDto(AVG(p.age), MAX(p.age)) FROM Pet p")
    PetStatisticsDto getPetStatistics();
}