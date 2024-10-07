package com.praveenukkoji.userservice.service;

import com.praveenukkoji.userservice.dto.request.auth.UserLoginRequest;
import com.praveenukkoji.userservice.dto.response.auth.UserLoginResponse;
import com.praveenukkoji.userservice.exception.auth.UserLoginException;
import com.praveenukkoji.userservice.model.User;
import com.praveenukkoji.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class AuthService {
    private final UserRepository userRepository;

    // login
    public UserLoginResponse login(UserLoginRequest userLoginRequest)
            throws UserLoginException {

        log.info("Login request: {}", userLoginRequest);
        
        String username = userLoginRequest.getUsername();
        String password = userLoginRequest.getPassword();

        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);

        if (user.isPresent()) {
            if (!user.get().getIsActive()) {
                throw new UserLoginException("account with username = " + username + " is de-activated contact admin");
            }
            String token = user.get().getId().toString();

            return UserLoginResponse.builder()
                    .token(token).build();
        }

        throw new UserLoginException("bad credentials or user does not exist");
    }
}
