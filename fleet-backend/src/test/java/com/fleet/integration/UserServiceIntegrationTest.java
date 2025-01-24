package com.fleet.integration;

import com.fleet.domain.dto.RegisterFormDTO;
import com.fleet.domain.po.User;
import com.fleet.domain.po.University;
import com.fleet.repository.UniversityRepository;
import com.fleet.repository.UserRepository;
import com.fleet.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test") // Will use application-test.yaml
//@Transactional // This will rollback the transaction after each test
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UniversityRepository universityRepository;

    private University testUniversity;

    @BeforeEach
    void setUp() {
        // Create a test university
        University university = new University();
        university.setName("Test University");
        university.setLocation("Test Location");
        testUniversity = universityRepository.save(university);
    }

    @Test
    void registerUser_ShouldSaveToDatabase() {
        // Arrange
        RegisterFormDTO registerDTO = new RegisterFormDTO();
        registerDTO.setUsername("integrationTest");
        registerDTO.setEmail("integration@test.com");
        registerDTO.setPassword("password123");
        registerDTO.setUniversity_id(testUniversity.getUniversity_id());

        // Act
        User savedUser = userService.registerUser(registerDTO);

        // Assert
        assertNotNull(savedUser.getUser_id());
        
        // Verify we can retrieve from database
        User retrievedUser = userRepository.findByEmail("integration@test.com");
        assertNotNull(retrievedUser);
        assertEquals("integrationTest", retrievedUser.getName());
        assertEquals(testUniversity.getUniversity_id(), retrievedUser.getUniversity().getUniversity_id());
        assertNotEquals("password123", retrievedUser.getPassword_hash()); // Password should be hashed
    }

    @Test
    void registerUser_WithDuplicateEmail_ShouldThrowException() {
        // Arrange
        RegisterFormDTO firstUser = new RegisterFormDTO();
        firstUser.setUsername("first");
        firstUser.setEmail("duplicate@test.com");
        firstUser.setPassword("password123");
        firstUser.setUniversity_id(testUniversity.getUniversity_id());

        RegisterFormDTO secondUser = new RegisterFormDTO();
        secondUser.setUsername("second");
        secondUser.setEmail("duplicate@test.com"); // Same email
        secondUser.setPassword("password456");
        secondUser.setUniversity_id(testUniversity.getUniversity_id());

        // Act & Assert
        userService.registerUser(firstUser); // First registration should succeed
        
        assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(secondUser);
        });
    }
} 