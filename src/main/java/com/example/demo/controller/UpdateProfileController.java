package com.example.demo.controller;

import com.example.demo.model.Profile;
import com.example.demo.service.UpdateProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/profiles")
public class UpdateProfileController {


    private final UpdateProfileService updateProfileService;

    @Autowired
    public UpdateProfileController(UpdateProfileService updateProfileService) {
        this.updateProfileService = updateProfileService;
    }

    // Update Profile
    @PutMapping("/{id}")
    public ResponseEntity<Profile> updateProfile(@PathVariable Long id, @RequestBody Profile profile) {
        Profile updatedProfile = updateProfileService.updateProfile(id, profile);
        return updatedProfile != null ? ResponseEntity.ok(updatedProfile) : ResponseEntity.notFound().build();
    }


}
