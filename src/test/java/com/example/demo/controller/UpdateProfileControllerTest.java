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
        Profile updatedProfile = new Profile(1L, "John Updated", "john.updated@example.com", "111111111");
        when(updateProfileService.updateProfile(eq(1L), any(Profile.class))).thenReturn(updatedProfile);

        mockMvc.perform(put("/api/profiles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProfile)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Updated"));

        verify(updateProfileService, times(1)).updateProfile(eq(1L), any(Profile.class));
    }

}
