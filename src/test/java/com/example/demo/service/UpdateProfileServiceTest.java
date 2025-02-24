package com.example.demo.service;

import com.example.demo.controller.UpdateProfileController;
import com.example.demo.model.Profile;
import com.example.demo.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateProfileServiceTest {

    @InjectMocks
    private UpdateProfileController updateProfileController;

    @Mock
    private ProfileRepository profileRepository;


    @InjectMocks
    private UpdateProfileService updateProfileService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(updateProfileController).build();
    }

    Profile profile1 = new Profile(1L, "John Doe", "john@example.com", "123456789");

    @Test
    void testUpdateProfile() {
        Profile updatedProfile = new Profile(1L, "John Updated", "john.updated@example.com", "111111111");
        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile1));
        when(profileRepository.save(any(Profile.class))).thenReturn(updatedProfile);

        Profile result = updateProfileService.updateProfile(1L, updatedProfile);

        assertNotNull(result);
        assertEquals("John Updated", result.getName());
        verify(profileRepository, times(1)).save(any(Profile.class));
    }

    @Test
    void testUpdateProfile_ProfileNotFound_ThrowsException() {
        Long nonExistentId = 999L;
        Profile profile = new Profile();
        profile.setName("Updated Name");

        when(profileRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            updateProfileService.updateProfile(nonExistentId, profile);
        });

        assertEquals("Profile not found", exception.getMessage());
        verify(profileRepository, never()).save(any(Profile.class));
    }

}