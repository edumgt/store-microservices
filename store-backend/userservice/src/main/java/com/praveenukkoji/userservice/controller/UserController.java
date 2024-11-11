package com.praveenukkoji.userservice.controller;

import com.praveenukkoji.userservice.dto.error.ValidationResponse;
import com.praveenukkoji.userservice.dto.request.user.ChangePasswordRequest;
import com.praveenukkoji.userservice.dto.request.user.CreateUserRequest;
import com.praveenukkoji.userservice.exception.role.RoleNotFoundException;
import com.praveenukkoji.userservice.exception.user.*;
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
    ) throws RoleNotFoundException, UserCreateException, PasswordEncryptionException {
        return ResponseEntity.status(201).body(userService.createUser(createUserRequest));
    }

    // get
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

    // TODO: update map to class request
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

    // change password
    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody @Valid ChangePasswordRequest changePasswordRequest
    ) throws UserNotFoundException, UserUpdateException, PasswordEncryptionException {
        return ResponseEntity.status(200).body(userService.changePassword(changePasswordRequest));
    }

    // TODO: change parameters to request class
    // update active status
    @PatchMapping("/active-status")
    public ResponseEntity<?> updateActiveStatus(
            @RequestParam(defaultValue = "", name = "userId") String userId,
            @RequestParam(defaultValue = "", name = "activeStatus") String activeStatus
    ) throws UserUpdateException {
        if (Objects.equals(userId, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("userId", "user id is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        if (Objects.equals(activeStatus, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("activeStatus", "active status is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        if (!activeStatus.equalsIgnoreCase("true") &&
                !activeStatus.equalsIgnoreCase("false")) {
            Map<String, String> error = new HashMap<>();
            error.put("activeStatus", "active status should be either true or false");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(userId);
        boolean status = activeStatus.equalsIgnoreCase("true");

        return ResponseEntity.status(200).body(userService.updateActiveStatus(id, status));
    }
}
