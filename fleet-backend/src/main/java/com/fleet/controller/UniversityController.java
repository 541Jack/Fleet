package com.fleet.controller;

import com.fleet.domain.po.University;
import com.fleet.service.UniversityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "University related Apis")
@RestController
@RequestMapping("/universities")
@RequiredArgsConstructor
public class UniversityController {
    private final UniversityService universityService;

    @ApiOperation("Create new university")
    @PostMapping
    public ResponseEntity<University> createUniversity(@RequestBody University university) {
        return ResponseEntity.ok(universityService.saveUniversity(university));
    }

    @ApiOperation("Get university by id")
    @GetMapping("/{id}")
    public ResponseEntity<University> getUniversity(@PathVariable Long id) {
        return ResponseEntity.ok(universityService.findById(id));
    }

    @ApiOperation("Get all universities")
    @GetMapping
    public ResponseEntity<List<University>> getAllUniversities() {
        return ResponseEntity.ok(universityService.findAll());
    }

    @ApiOperation("Update university")
    @PutMapping("/{id}")
    public ResponseEntity<University> updateUniversity(@PathVariable Long id, @RequestBody University university) {
        return ResponseEntity.ok(universityService.updateUniversity(id, university));
    }

    @ApiOperation("Delete university")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUniversity(@PathVariable Long id) {
        universityService.deleteUniversity(id);
        return ResponseEntity.ok().build();
    }
} 