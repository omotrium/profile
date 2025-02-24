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

    Profile testProfile = new Profile(1L, "John Doe", "john@example.com", "1234567890");


    @Test
    void testCreateProfile() throws Exception {
        when(createProfileService.createProfile(any(Profile.class))).thenReturn(testProfile);

        mockMvc.perform(post("/api/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testProfile)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(createProfileService, times(1)).createProfile(any(Profile.class));
    }

    @Test
    void testCreateProfile_BadRequest_MissingFields() throws Exception {
        Profile invalidProfile = new Profile(); // Missing fields

        mockMvc.perform(post("/api/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidProfile)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name is mandatory"))
                .andExpect(jsonPath("$.email").value("Email is mandatory"))
                .andExpect(jsonPath("$.phone").value("Phone number is mandatory"));

        verify(createProfileService, never()).createProfile(any(Profile.class));
    }

    @Test
    void testCreateProfile_BadRequest_InvalidEmail() throws Exception {
        Profile invalidProfile = new Profile(1L, "John Doe", "invalid-email", "1234567890");

        mockMvc.perform(post("/api/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidProfile)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("Email should be valid"));

        verify(createProfileService, never()).createProfile(any(Profile.class));
    }

    @Test
    void testCreateProfileWithInvalidName() throws Exception {
        Profile profile = new Profile(1L,"", "", "1234567890"); // Empty name

        mockMvc.perform(post("/api/profiles")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(profile)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("Email is mandatory"))
                .andExpect(jsonPath("$.name").value("Name must be between 2 and 50 characters"));
    }
}
