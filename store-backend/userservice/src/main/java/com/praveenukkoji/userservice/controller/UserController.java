package com.praveenukkoji.userservice.controller;

import com.praveenukkoji.userservice.dto.error.ValidationResponse;
import com.praveenukkoji.userservice.dto.request.user.CreateUserRequest;
import com.praveenukkoji.userservice.exception.role.RoleNotFoundException;
import com.praveenukkoji.userservice.exception.user.UserCreateException;
import com.praveenukkoji.userservice.exception.user.UserDeleteException;
import com.praveenukkoji.userservice.exception.user.UserNotFoundException;
import com.praveenukkoji.userservice.exception.user.UserUpdateException;
import com.praveenukkoji.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private final UserService userService;

    // create
    @PostMapping(path = "")
    public ResponseEntity<?> createUser(
            @RequestBody @Valid CreateUserRequest createUserRequest
    ) throws RoleNotFoundException, UserCreateException {
        return ResponseEntity.status(201).body(userService.createUser(createUserRequest));
    }

    // retrieve
    @GetMapping(path = "")
    public ResponseEntity<?> getUser(
            @RequestParam(defaultValue = "", name = "userId") String userId
    ) throws UserNotFoundException {
        if (Objects.equals(userId, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("userId", "user id is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(userId);
        return ResponseEntity.status(200).body(userService.getUser(id));
    }

    // update
    @PatchMapping(path = "")
    public ResponseEntity<?> updateUser(
            @RequestParam(defaultValue = "", name = "userId") String userId,
            @RequestBody Map<String, String> updates
    ) throws UserNotFoundException, UserUpdateException {
        if (Objects.equals(userId, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("userId", "user id is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(userId);
        return ResponseEntity.status(200).body(userService.updateUser(id, updates));
    }

    // delete
    @DeleteMapping(path = "")
    public ResponseEntity<?> deleteUser(
            @RequestParam(defaultValue = "", name = "userId") String userId
    ) throws UserNotFoundException, UserDeleteException {
        if (Objects.equals(userId, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("userId", "user id is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(userId);
        userService.deleteUser(id);

        return ResponseEntity.status(204).body("");
    }
}
