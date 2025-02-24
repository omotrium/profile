package com.example.demo.service;

import com.example.demo.controller.DeleteProfileController;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class DeleteProfileServiceTest {

    @InjectMocks
    private DeleteProfileController deleteProfileController;

    @Mock
    private ProfileRepository profileRepository;


    @InjectMocks
    private DeleteProfileService deleteProfileService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(deleteProfileController).build();
    }

    @Test
    void testDeleteProfile_Success() {
        Profile profile = new Profile(1L, "John Doe", "john@example.com", "123456789");
        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));

        deleteProfileService.deleteProfile(1L);

        verify(profileRepository, times(1)).delete(profile);
    }

    @Test
    void testDeleteProfile_NotFound() {
        Long invalidId = 999L;
        when(profileRepository.findById(invalidId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> deleteProfileService.deleteProfile(invalidId));

        assertEquals("Profile not found", exception.getMessage());
        verify(profileRepository, never()).delete(any());
    }
}