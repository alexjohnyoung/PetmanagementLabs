package com.example.Lab5AppDev.service;

import com.example.Lab5AppDev.entity.Household;
import com.example.Lab5AppDev.repository.HouseholdRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HouseholdServiceTest {

    @Mock
    private HouseholdRepository householdRepository;

    @InjectMocks
    private HouseholdServiceImpl householdService;


    @Test
    void testGetHouseholdById() {
        Household household = new Household("D01AB12", 3, 5, true, null);
        when(householdRepository.findById("D01AB12")).thenReturn(Optional.of(household));

        Household result = householdService.getHouseholdById("D01AB12");
        assertThat(result.getEircode()).isEqualTo("D01AB12");
    }

    @Test
    void testDeleteHousehold() {
        doNothing().when(householdRepository).deleteById("D01AB12");

        householdService.deleteHousehold("D01AB12");
        verify(householdRepository, times(1)).deleteById("D01AB12");
    }
}
