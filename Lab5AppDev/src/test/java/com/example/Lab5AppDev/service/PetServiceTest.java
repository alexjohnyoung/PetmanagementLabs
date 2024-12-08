package com.example.Lab5AppDev.service;

import com.example.Lab5AppDev.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetServiceImpl petService;

    @Test
    void testDeletePetById() {
        doNothing().when(petRepository).deleteById(1L);

        petService.deletePetById(1L);
        verify(petRepository, times(1)).deleteById(1L);
    }
}
