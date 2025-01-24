package com.fleet.service;

import com.fleet.domain.po.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.fleet.repository.UserRepository;
import com.fleet.mapper.UserMapper;
import com.fleet.domain.dto.RegisterFormDTO;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository; //SpringBoot injection of JPA
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Saves the User entity to DB
     * @param user
     * @return
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Returns the User entity based on ID
     * @param id
     * @return
     */
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPassword_hash(userDetails.getPassword_hash());
        user.setAverage_rating(userDetails.getAverage_rating());
        user.setUpdated_at(userDetails.getUpdated_at());
        return userRepository.save(user);
    }

    // Delete
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }

    /**
     * Register a new user
     * @param registerDTO
     * @return
     */
    public User registerUser(RegisterFormDTO registerDTO) {
        User user = userMapper.toEntity(registerDTO);
        // Set average rating to null for initial registration
        user.setAverage_rating(null);
        // Hash the password
        user.setPassword_hash(passwordEncoder.encode(registerDTO.getPassword()));
        return userRepository.save(user);
    }
}

