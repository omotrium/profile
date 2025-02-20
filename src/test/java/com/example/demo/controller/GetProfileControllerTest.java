package com.example.demo.controller;

import com.example.demo.model.Profile;
import com.example.demo.service.GetProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class GetProfileControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GetProfileService getProfileService;

    @InjectMocks
    private GetProfileController getProfileController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(getProfileController).build(); // ✅ Initialize MockMvc
    }

    Profile testProfile = new Profile(1L, "John Doe", "john@example.com", "123456789");

    @Test
    void testGetAllProfiles() throws Exception {

        when(getProfileService.getAllProfiles()).thenReturn(List.of(testProfile));

        mockMvc.perform(get("/api/profiles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"));

        verify(getProfileService, times(1)).getAllProfiles();
    }


    @Test
    void testGetProfileById() throws Exception {
        when(getProfileService.getProfileById(1L)).thenReturn(Optional.of(testProfile));

        mockMvc.perform(get("/api/profiles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(getProfileService, times(1)).getProfileById(1L);
    }

}
