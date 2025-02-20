package com.example.demo.controller;

import com.example.demo.service.DeleteProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profiles")
public class DeleteProfileController {


    private final DeleteProfileService deleteProfileService;

    @Autowired
    public DeleteProfileController(DeleteProfileService deleteProfileService) {
        this.deleteProfileService = deleteProfileService;
    }

    // Delete Profile
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        deleteProfileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }
}
