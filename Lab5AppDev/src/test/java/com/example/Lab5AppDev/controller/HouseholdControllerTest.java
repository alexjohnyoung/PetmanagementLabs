package com.example.Lab5AppDev.controller;

import com.example.Lab5AppDev.service.HouseholdService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HouseholdController.class)
class HouseholdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private HouseholdService householdService;

    @Test
    void testGetAllHouseholds() throws Exception {
        mockMvc.perform(get("/api/households"))
                .andExpect(status().isOk());
    }
}
