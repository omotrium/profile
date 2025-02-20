package com.example.demo.controller;

import com.example.demo.service.DeleteProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DeleteProfileControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DeleteProfileService deleteProfileService;

    @InjectMocks
    private DeleteProfileController deleteProfileController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(deleteProfileController).build(); // ✅ Initialize MockMvc
    }

    @Test
    void testDeleteProfile() throws Exception {
        doNothing().when(deleteProfileService).deleteProfile(1L);

        mockMvc.perform(delete("/api/profiles/1"))
                .andExpect(status().isNoContent());

        verify(deleteProfileService, times(1)).deleteProfile(1L);
    }
}
