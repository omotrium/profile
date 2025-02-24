package com.example.demo.controller;

import com.example.demo.model.Profile;
import com.example.demo.service.UpdateProfileService;
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
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UpdateProfileControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UpdateProfileService updateProfileService;

    @InjectMocks
    private UpdateProfileController updateProfileController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(updateProfileController).build(); // ✅ Initialize MockMvc
    }

    @Test
    void testUpdateProfile() throws Exception {
        Profile updatedProfile = new Profile(1L, "John Updated", "john.updated@example.com", "1111111110");
        when(updateProfileService.updateProfile(eq(1L), any(Profile.class))).thenReturn(updatedProfile);

        mockMvc.perform(put("/api/profiles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProfile)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Updated"));

        verify(updateProfileService, times(1)).updateProfile(eq(1L), any(Profile.class));
    }

    @Test
    void testUpdateProfile_BadRequest_MissingFields() throws Exception {
        Profile invalidProfile = new Profile(); // Missing fields

        mockMvc.perform(put("/api/profiles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidProfile)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name is mandatory"))
                .andExpect(jsonPath("$.email").value("Email is mandatory"))
                .andExpect(jsonPath("$.phone").value("Phone number is mandatory"));

        verify(updateProfileService, never()).updateProfile(eq(1L), any(Profile.class));
    }

    @Test
    void testUpdateProfile_BadRequest_InvalidEmail() throws Exception {
        Profile invalidProfile = new Profile(1L, "John Doe", "invalid-email", "1234567890");

        mockMvc.perform(put("/api/profiles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidProfile)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("Email should be valid"));

        verify(updateProfileService, never()).updateProfile(eq(1L), any(Profile.class));
    }

    @Test
    void testUpdateProfile_NotFound() throws Exception {
        Long nonExistingProfileId = 999L;
        when(updateProfileService.updateProfile(eq(nonExistingProfileId), any(Profile.class))).thenReturn(null);

        mockMvc.perform(put("/api/profiles/{id}", nonExistingProfileId)
                        .contentType("application/json")
                        .content("{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\",\"phone\":\"1234567890\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateProfileWithInvalidName() throws Exception {
        Profile profile = new Profile(1L,"", "", "1234567890"); // Empty name

        mockMvc.perform(put("/api/profiles/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(profile)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("Email is mandatory"))
                .andExpect(jsonPath("$.name").value("Name must be between 2 and 50 characters"));
    }

}
