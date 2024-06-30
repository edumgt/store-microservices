package com.praveenukkoji.userservice.controller;

import com.praveenukkoji.userservice.dto.request.CreateUserRequest;
import com.praveenukkoji.userservice.dto.response.GetUserResponse;
import com.praveenukkoji.userservice.exception.CreateUserException;
import com.praveenukkoji.userservice.exception.UserNotFoundException;
import com.praveenukkoji.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/create")
    public ResponseEntity<UUID> createUser(@RequestBody CreateUserRequest createUserRequest)
            throws CreateUserException {
        return ResponseEntity.status(201).body(userService.createUser(createUserRequest));
    }

    @GetMapping(path = "/get/{userId}")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable UUID userId)
            throws UserNotFoundException {
        return ResponseEntity.status(200).body(userService.getUser(userId));
    }

    @DeleteMapping(path = "/delete/{userId}")
    public ResponseEntity<UUID> deleteUser(@PathVariable UUID userId)
            throws UserNotFoundException {
        return ResponseEntity.status(200).body(userService.deleteUser(userId));
    }
}
