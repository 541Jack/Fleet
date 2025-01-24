package com.fleet.service;

import com.fleet.domain.po.University;
import com.fleet.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityService {
    private final UniversityRepository universityRepository;

    public University saveUniversity(University university) {
        university.setCreated_at(LocalDateTime.now());
        university.setUpdated_at(LocalDateTime.now());
        return universityRepository.save(university);
    }

    public University findById(Long id) {
        return universityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("University not found with id: " + id));
    }

    public List<University> findAll() {
        return universityRepository.findAll();
    }

    public University updateUniversity(Long id, University universityDetails) {
        University university = findById(id);
        university.setName(universityDetails.getName());
        university.setLocation(universityDetails.getLocation());
        university.setUpdated_at(LocalDateTime.now());
        return universityRepository.save(university);
    }

    public void deleteUniversity(Long id) {
        University university = findById(id);
        universityRepository.delete(university);
    }
} 