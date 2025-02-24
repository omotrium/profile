package com.example.demo.controller;

import com.example.demo.model.Profile;
import com.example.demo.service.CreateProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/profiles")
@Validated
public class CreateProfileController {

    private final CreateProfileService  createProfileService;

    @Autowired
    public CreateProfileController(CreateProfileService  createProfileService) {
        this.createProfileService = createProfileService;
    }

    @PostMapping
    public ResponseEntity<?> createProfile(@Valid @RequestBody Profile profile, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        Profile savedProfile = createProfileService.createProfile(profile);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProfile);
    }
}
