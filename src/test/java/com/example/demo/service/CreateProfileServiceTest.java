package com.example.demo.service;

import com.example.demo.controller.CreateProfileController;
import com.example.demo.model.Profile;
import com.example.demo.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateProfileServiceTest {

    @InjectMocks
    private CreateProfileController createProfileController;

    @Mock
    private ProfileRepository profileRepository;


    @InjectMocks
    private CreateProfileService createProfileService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(createProfileController).build();
    }

    Profile profile1 = new Profile(1L, "John Doe", "john@example.com", "123456789");

    @Test
    void testCreateProfile() {
        when(profileRepository.save(profile1)).thenReturn(profile1);

        Profile result = createProfileService.createProfile(profile1);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(profileRepository, times(1)).save(profile1);
    }

    @Test
    void testCreateProfile_DuplicateEmail_ThrowsException() {
        Profile profile = new Profile();
        profile.setName("John Doe");
        profile.setEmail("john@example.com");

        when(profileRepository.existsByEmail(profile.getEmail())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            createProfileService.createProfile(profile);
        });

        assertTrue(exception.getMessage().contains("Email is already in use"));
        verify(profileRepository, never()).save(any(Profile.class));
    }
}