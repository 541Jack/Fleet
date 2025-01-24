package com.fleet.controller;

import com.fleet.domain.dto.RegisterFormDTO;
import com.fleet.domain.po.User;
import com.fleet.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Api(tags = "User related Apis")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ApiOperation("Find user by id")
    @GetMapping("{id}")
    public Optional<User> queryOrderById(@PathVariable("id") Long orderId) {
        return userService.findUserById(orderId);
    }

    @ApiOperation("Register a new user")
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegisterFormDTO registerDTO) {
        User registeredUser = userService.registerUser(registerDTO);
        return ResponseEntity.ok(registeredUser);
    }

}
