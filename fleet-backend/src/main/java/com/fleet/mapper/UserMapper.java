package com.fleet.mapper;

import com.fleet.domain.dto.RegisterFormDTO;
import com.fleet.domain.po.University;
import com.fleet.domain.po.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {
    public User toEntity(RegisterFormDTO dto) {
        User user = new User();
        user.setName(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword_hash(dto.getPassword()); // Note: You should hash this password
        if (dto.getUniversity_id() != null) {
            University university = new University();
            university.setUniversity_id(dto.getUniversity_id());
            user.setUniversity(university);
        }
        user.setCreated_at(LocalDateTime.now());
        user.setUpdated_at(LocalDateTime.now());
        return user;
    }
} 