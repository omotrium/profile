package com.example.demo.controller;

import com.example.demo.model.Profile;
import com.example.demo.service.UpdateProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


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
    public ResponseEntity<?> updateProfile(@PathVariable Long id, @Valid @RequestBody Profile profile, BindingResult result) {
                if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        Profile updatedProfile = updateProfileService.updateProfile(id, profile);
        return updatedProfile != null ? ResponseEntity.ok(updatedProfile) : ResponseEntity.notFound().build();
    }

}
