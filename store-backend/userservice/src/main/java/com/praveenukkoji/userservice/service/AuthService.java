package com.praveenukkoji.userservice.service;

import com.praveenukkoji.userservice.dto.request.auth.UserLoginRequest;
import com.praveenukkoji.userservice.dto.response.auth.UserLoginResponse;
import com.praveenukkoji.userservice.exception.auth.UserLoginException;
import com.praveenukkoji.userservice.exception.user.PasswordDecryptionException;
import com.praveenukkoji.userservice.model.User;
import com.praveenukkoji.userservice.repository.UserRepository;
import com.praveenukkoji.userservice.utility.UserUtility;
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

    private final UserUtility userUtility;

    // login
    public UserLoginResponse login(UserLoginRequest userLoginRequest)
            throws UserLoginException, PasswordDecryptionException {

        log.info("Login request: {}", userLoginRequest);

        String username = userLoginRequest.getUsername();
        String password = userLoginRequest.getPassword();

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            if (!user.get().getIsActive()) {
                throw new UserLoginException("account with username = " + username + " is de-activated contact admin");
            }

            boolean passwordMatch = password.equals(userUtility.getDecryptedPassword(user.get().getPassword()));

            if (!passwordMatch) {
                throw new UserLoginException("incorrect password");
            }

            String token = user.get().getId().toString();

            return UserLoginResponse.builder()
                    .token(token).build();
        }

        throw new UserLoginException("bad credentials or user does not exist");
    }
}
