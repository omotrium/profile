package com.example.demo.controller;

import com.example.demo.model.Profile;
import com.example.demo.service.CreateProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/profiles")
public class CreateProfileController {

    private final CreateProfileService  createProfileService;

    @Autowired
    public CreateProfileController(CreateProfileService  createProfileService) {
        this.createProfileService = createProfileService;
    }

    // Create Profile
    @PostMapping
    public Profile createProfile(@RequestBody Profile profile) {
        return createProfileService.createProfile(profile);
    }

}
