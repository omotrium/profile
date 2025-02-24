package com.example.demo.controller;

import com.example.demo.exception.ErrorResponse;
import com.example.demo.model.Profile;
import com.example.demo.service.GetProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/profiles")
public class GetProfileController {

    private final GetProfileService getProfileService;

    @Autowired
    public GetProfileController(GetProfileService getProfileService) {
        this.getProfileService = getProfileService;
    }

    @GetMapping
    public ResponseEntity<List<Profile>> getAllProfiles() {
        List<Profile> profiles = getProfileService.getAllProfiles();
        return ResponseEntity.ok(profiles);
    }

    // Get Profile by ID
    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable Long id) {
        Optional<Profile> profile = getProfileService.getProfileById(id);
        return profile.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }



}

