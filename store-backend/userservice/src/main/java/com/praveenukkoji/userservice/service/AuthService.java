package com.praveenukkoji.userservice.service;

import com.praveenukkoji.userservice.dto.request.auth.UserLoginRequest;
import com.praveenukkoji.userservice.dto.response.auth.UserLoginResponse;
import com.praveenukkoji.userservice.exception.auth.UserLoginException;
import com.praveenukkoji.userservice.model.User;
import com.praveenukkoji.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {
    private final UserRepository userRepository;

    // login
    public UserLoginResponse login(UserLoginRequest userLoginRequest)
            throws UserLoginException {
        String username = userLoginRequest.getUsername();
        String password = userLoginRequest.getPassword();

        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);

        if (user.isPresent()) {
            String token = user.get().getId().toString();
            
            return UserLoginResponse.builder()
                    .token(token).build();
        }

        throw new UserLoginException("bad credentials or user does not exist");
    }
}
