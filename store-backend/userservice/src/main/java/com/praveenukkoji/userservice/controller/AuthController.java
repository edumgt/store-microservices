package com.praveenukkoji.userservice.controller;

import com.praveenukkoji.userservice.dto.request.auth.UserLoginRequest;
import com.praveenukkoji.userservice.exception.auth.UserLoginException;
import com.praveenukkoji.userservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    // login
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody @Valid UserLoginRequest userLoginRequest
    ) throws UserLoginException {
        return ResponseEntity.status(200).body(authService.login(userLoginRequest));
    }
}
