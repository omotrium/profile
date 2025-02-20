package com.example.demo.service;


import com.example.demo.model.Profile;
import com.example.demo.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UpdateProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public Profile updateProfile(Long id, Profile updatedProfile) {
        return profileRepository.findById(id).map(profile -> {
            profile.setName(updatedProfile.getName());
            profile.setEmail(updatedProfile.getEmail());
            profile.setPhone(updatedProfile.getPhone());
            return profileRepository.save(profile);
        }).orElse(null);
    }

}
