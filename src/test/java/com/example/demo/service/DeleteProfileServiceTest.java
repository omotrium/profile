package com.example.demo.service;

import com.example.demo.controller.DeleteProfileController;
import com.example.demo.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
    void testDeleteProfile() {
        doNothing().when(profileRepository).deleteById(1L);

        deleteProfileService.deleteProfile(1L);

        verify(profileRepository, times(1)).deleteById(1L);
    }
}