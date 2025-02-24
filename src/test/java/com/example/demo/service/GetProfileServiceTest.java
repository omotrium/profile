package com.example.demo.service;

import com.example.demo.controller.GetProfileController;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetProfileServiceTest {

    @InjectMocks
    private GetProfileController getProfileController;

    @Mock
    private ProfileRepository profileRepository;


    @InjectMocks
    private GetProfileService getProfileService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(getProfileController).build();
    }

    Profile profile1 = new Profile(1L, "John Doe", "john@example.com", "123456789");
    Profile profile2 = new Profile(2L, "John Doe", "john@example.com", "123456789");


    @Test
    void testGetAllProfiles() {
        List<Profile> profiles = Arrays.asList(profile1, profile2);
        when(profileRepository.findAll()).thenReturn(profiles);

        List<Profile> result = getProfileService.getAllProfiles();

        assertEquals(2, result.size());
        verify(profileRepository, times(1)).findAll();
    }

    @Test
    void testGetProfileById() {

        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile1));

        Optional<Profile> result = getProfileService.getProfileById(1L);

        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
        verify(profileRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProfileById_NotFound_ThrowsException() {
        Long nonExistentId = 999L;

        when(profileRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            getProfileService.getProfileById(nonExistentId).orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        });

        assertEquals("Profile not found", exception.getMessage());
    }
}
