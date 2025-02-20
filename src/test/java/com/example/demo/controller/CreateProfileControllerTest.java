package com.example.demo.controller;

import com.example.demo.model.Profile;
import com.example.demo.service.CreateProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CreateProfileControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CreateProfileService createProfileService;

    @InjectMocks
    private CreateProfileController createProfileController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(createProfileController).build(); // ✅ Initialize MockMvc
    }

    Profile testProfile = new Profile(1L, "John Doe", "john@example.com", "123456789");


    @Test
    void testCreateProfile() throws Exception {
        when(createProfileService.createProfile(any(Profile.class))).thenReturn(testProfile);

        mockMvc.perform(post("/api/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testProfile)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(createProfileService, times(1)).createProfile(any(Profile.class));
    }

}
