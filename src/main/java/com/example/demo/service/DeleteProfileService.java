package com.example.demo.service;



import com.example.demo.model.Profile;
import com.example.demo.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DeleteProfileService {

    @Autowired
    private ProfileRepository profileRepository;


    public void deleteProfile(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        profileRepository.delete(profile);
    }

}
