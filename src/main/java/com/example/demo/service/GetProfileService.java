package com.example.demo.service;


import com.example.demo.exception.ProfileNotFoundException;
import com.example.demo.model.Profile;
import com.example.demo.repository.ProfileRepository;
import io.micrometer.observation.ObservationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GetProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }



    public Optional<Profile> getProfileById(Long id) {
        return profileRepository.findById(id);
    }


}
