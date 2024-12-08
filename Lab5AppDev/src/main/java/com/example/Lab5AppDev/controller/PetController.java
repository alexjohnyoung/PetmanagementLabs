package com.example.Lab5AppDev.controller;

import com.example.Lab5AppDev.dto.PetDto;
import com.example.Lab5AppDev.dto.PetStatisticsDto;
import com.example.Lab5AppDev.entity.Pet;
import com.example.Lab5AppDev.service.PetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public ResponseEntity<List<Pet>> getAllPets() {
        return ResponseEntity.ok(petService.getAllPets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPet(@PathVariable Long id) {
        return ResponseEntity.ok(petService.getPetById(id));
    }

    @PostMapping
    public ResponseEntity<Pet> createPet(@Valid @RequestBody Pet pet) {
        return ResponseEntity.ok(petService.createPet(pet));
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<Pet> changePetName(@PathVariable Long id, @RequestParam String name) {
        return ResponseEntity.ok(petService.changePetName(id, name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.deletePetById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statistics")
    public ResponseEntity<PetStatisticsDto> getPetStatistics() {
        return ResponseEntity.ok(petService.getPetStatistics());
    }
}
