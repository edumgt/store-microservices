package com.praveenukkoji.userservice.controller;

import com.praveenukkoji.userservice.dto.error.ErrorResponse;
import com.praveenukkoji.userservice.dto.request.user.CreateUserRequest;
import com.praveenukkoji.userservice.dto.request.user.LoginUserRequest;
import com.praveenukkoji.userservice.exception.role.RoleNotFoundException;
import com.praveenukkoji.userservice.exception.user.UserCreateException;
import com.praveenukkoji.userservice.exception.user.UserNotFoundException;
import com.praveenukkoji.userservice.exception.user.UserUpdateException;
import com.praveenukkoji.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/create")
    public ResponseEntity<?> createUser(
            @RequestBody @Valid CreateUserRequest createUserRequest
    ) throws UserCreateException, RoleNotFoundException {

        return ResponseEntity.status(201).body(userService.createUser(createUserRequest));
    }

    @GetMapping(path = "/get")
    public ResponseEntity<?> getUser(
            @RequestParam(defaultValue = "", name = "userId") String userId
    ) throws UserNotFoundException {

        if (Objects.equals(userId, "")) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .message("user id is empty")
                    .build();
            return ResponseEntity.status(400).body(errorResponse);
        }

        UUID id = UUID.fromString(userId);
        return ResponseEntity.status(200).body(userService.getUser(id));
    }

    @PatchMapping(path = "/update")
    public ResponseEntity<?> updateUser(
            @RequestParam(defaultValue = "", name = "userId") String userId,
            @RequestBody Map<String, String> updates
    ) throws UserNotFoundException, UserUpdateException {

        if (Objects.equals(userId, "")) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .message("user id is empty")
                    .build();
            return ResponseEntity.status(400).body(errorResponse);
        }

        UUID id = UUID.fromString(userId);
        return ResponseEntity.status(200).body(userService.updateUser(id, updates));
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> deleteUser(
            @RequestParam(defaultValue = "", name = "userId") String userId
    ) throws UserNotFoundException {

        if (Objects.equals(userId, "")) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .message("user id is empty")
                    .build();
            return ResponseEntity.status(400).body(errorResponse);
        }

        UUID id = UUID.fromString(userId);
        return ResponseEntity.status(200).body(userService.deleteUser(id));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> getUserId(
            @RequestBody @Valid LoginUserRequest loginUserRequest
    ) throws UserNotFoundException {

        return ResponseEntity.status(200).body(userService.getUserId(loginUserRequest));
    }
}
