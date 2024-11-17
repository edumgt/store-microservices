package com.praveenukkoji.userservice.controller;

import com.praveenukkoji.userservice.dto.user.request.ChangePasswordRequest;
import com.praveenukkoji.userservice.dto.user.request.CreateUserRequest;
import com.praveenukkoji.userservice.dto.user.request.UpdateActiveStatusRequest;
import com.praveenukkoji.userservice.dto.user.request.UpdateUserRequest;
import com.praveenukkoji.userservice.exception.error.ValidationException;
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
    ) throws RoleNotFoundException, UserCreateException, PasswordEncryptionException, ValidationException {
        return ResponseEntity.status(201).body(userService.createUser(createUserRequest));
    }

    // get
    @GetMapping(path = "")
    public ResponseEntity<?> getUser(
            @RequestParam(defaultValue = "", name = "userId") String userId
    ) throws UserNotFoundException, ValidationException {
        return ResponseEntity.status(200).body(userService.getUser(userId));
    }

    // TODO: update map to class request
    // update
    @PatchMapping(path = "")
    public ResponseEntity<?> updateUser(
            @RequestBody @Valid UpdateUserRequest updateUserRequest
    ) throws UserNotFoundException, UserUpdateException, ValidationException {
        userService.updateUser(updateUserRequest);
        return ResponseEntity.status(204).body("");
    }

    // delete
    @DeleteMapping(path = "")
    public ResponseEntity<?> deleteUser(
            @RequestParam(defaultValue = "", name = "userId") String userId
    ) throws UserNotFoundException, UserDeleteException, ValidationException {
        userService.deleteUser(userId);
        return ResponseEntity.status(204).body("");
    }

    // change password
    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody @Valid ChangePasswordRequest changePasswordRequest
    ) throws UserNotFoundException, UserUpdateException, PasswordEncryptionException, ValidationException {
        userService.changePassword(changePasswordRequest);
        return ResponseEntity.status(204).body("");
    }

    // TODO: change parameters to request class
    // update active status
    @PatchMapping("/active-status")
    public ResponseEntity<?> updateActiveStatus(
            @RequestBody @Valid UpdateActiveStatusRequest updateActiveStatusRequest
    ) throws UserUpdateException, UserNotFoundException, ValidationException {
        userService.updateActiveStatus(updateActiveStatusRequest);
        return ResponseEntity.status(204).body("");
    }
}
