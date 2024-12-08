package com.example.Lab5AppDev.controller;

import com.example.Lab5AppDev.entity.Pet;
import com.example.Lab5AppDev.service.PetService;
import org.mockito.InjectMocks;
import org.testng.annotations.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PetController.class)
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PetService petService;

    @Test
    void testGetAllPets() throws Exception {
        Pet pet1 = new Pet(1L, "Buddy", "Golden Retriever", "Dog", 3, null);
        Pet pet2 = new Pet(2L, "Kitty", "Siamese", "Cat", 2, null);

        when(petService.getAllPets()).thenReturn(Arrays.asList(pet1, pet2));

        mockMvc.perform(get("/api/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Buddy"))
                .andExpect(jsonPath("$[1].name").value("Kitty"));
    }

    @Test
    void testGetPetById() throws Exception {
        Pet pet = new Pet(1L, "Buddy", "Golden Retriever", "Dog", 3, null);
        when(petService.getPetById(1L)).thenReturn(pet);

        mockMvc.perform(get("/api/pets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Buddy"))
                .andExpect(jsonPath("$.breed").value("Golden Retriever"));
    }

    @Test
    void testCreatePet() throws Exception {
        Pet pet = new Pet(1L, "Buddy", "Golden Retriever", "Dog", 3, null);
        when(petService.createPet(any(Pet.class))).thenReturn(pet);

        mockMvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "name": "Buddy",
                            "breed": "Golden Retriever",
                            "type": "Dog",
                            "age": 3
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Buddy"));
    }

    @Test
    void testDeletePet() throws Exception {
        doNothing().when(petService).deletePetById(1L);

        mockMvc.perform(delete("/api/pets/1"))
                .andExpect(status().isNoContent());

        verify(petService, times(1)).deletePetById(1L);
    }
}
