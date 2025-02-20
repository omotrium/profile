package com.example.demo.service;



import com.example.demo.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DeleteProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public void deleteProfile(Long id) {
        profileRepository.deleteById(id);
    }
}
