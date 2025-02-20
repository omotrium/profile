package com.example.demo.service;


import com.example.demo.model.Profile;
import com.example.demo.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CreateProfileService {

    @Autowired
    private ProfileRepository profileRepository;


    public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
    }

}
