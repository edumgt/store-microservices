package com.praveenukkoji.userservice.service;

import com.praveenukkoji.userservice.dto.request.CreateUserRequest;
import com.praveenukkoji.userservice.dto.response.GetUserResponse;
import com.praveenukkoji.userservice.exception.CreateUserException;
import com.praveenukkoji.userservice.exception.UserNotFoundException;
import com.praveenukkoji.userservice.model.User;
import com.praveenukkoji.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UUID createUser(CreateUserRequest createUserRequest)
            throws CreateUserException {
        User newUser = User.builder()
                .username(createUserRequest.getUsername())
                .password(createUserRequest.getPassword())
                .email(createUserRequest.getEmail())
                .createdOn(LocalDate.now())
                .build();

        try {
            newUser = userRepository.saveAndFlush(newUser);
            log.info("user created with userId = {}", newUser.getUserId());
            return newUser.getUserId();
        } catch (Exception e) {
            throw new CreateUserException("unable to create user");
        }
    }

    public GetUserResponse getUser(UUID userId)
            throws UserNotFoundException {
        Optional<User> queryResult = userRepository.findById(userId);

        if (queryResult.isPresent()) {
            User user = queryResult.get();
            log.info("user fetched with userId = {}", userId);
            return GetUserResponse.builder()
                    .userId(user.getUserId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .createdOn(user.getCreatedOn())
                    .createdBy(user.getCreatedBy())
                    .modifiedBy(user.getModifiedBy())
                    .build();
        }

        throw new UserNotFoundException("user not found");
    }

    public UUID deleteUser(UUID userId)
            throws UserNotFoundException {
        Optional<User> queryResult = userRepository.findById(userId);

        if (queryResult.isPresent()) {
            userRepository.deleteById(userId);
            log.info("user deleted with userId = {}", userId);
            return userId;
        }

        throw new UserNotFoundException("user not found");
    }
}
