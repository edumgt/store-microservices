package com.praveenukkoji.userservice.service;

import com.praveenukkoji.userservice.dto.request.user.CreateUserRequest;
import com.praveenukkoji.userservice.dto.request.user.LoginUserRequest;
import com.praveenukkoji.userservice.dto.response.user.UserResponse;
import com.praveenukkoji.userservice.exception.role.RoleNotFoundException;
import com.praveenukkoji.userservice.exception.user.UserCreateException;
import com.praveenukkoji.userservice.exception.user.UserNotFoundException;
import com.praveenukkoji.userservice.exception.user.UserUpdateException;
import com.praveenukkoji.userservice.model.Role;
import com.praveenukkoji.userservice.model.User;
import com.praveenukkoji.userservice.repository.RoleRepository;
import com.praveenukkoji.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public UUID createUser(CreateUserRequest createUserRequest)
            throws UserCreateException, RoleNotFoundException {

        Optional<Role> role = roleRepository.findById(createUserRequest.getRoleId());

        if (role.isPresent()) {
            User newUser = User.builder()
                    .fullname(createUserRequest.getFullname())
                    .username(createUserRequest.getUsername())
                    .password(createUserRequest.getPassword())
                    .email(createUserRequest.getEmail())
                    .isActive(true)
                    .role(role.get())
                    .build();

            try {
                return userRepository.save(newUser).getId();
            } catch (Exception e) {
                throw new UserCreateException();
            }
        }

        throw new RoleNotFoundException();
    }

    public UserResponse getUser(UUID userId)
            throws UserNotFoundException {

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {

            return UserResponse.builder()
                    .id(user.get().getId())
                    .fullname(user.get().getFullname())
                    .username(user.get().getUsername())
                    .email(user.get().getEmail())
                    .role(user.get().getRole())
                    .addressList(user.get().getAddressList())
                    .build();
        }

        throw new UserNotFoundException();
    }

    @Transactional
    public UUID updateUser(UUID userId, Map<String, String> updates)
            throws UserNotFoundException, UserUpdateException {

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {

            for (Map.Entry<String, String> entry : updates.entrySet()) {
                switch (entry.getKey()) {
                    case "fullname":
                        if (!Objects.equals(entry.getValue(), "")) {
                            user.get().setFullname(entry.getValue());
                        }
                        break;
                    case "username":
                        if (!Objects.equals(entry.getValue(), "")) {
                            user.get().setUsername(entry.getValue());
                        }
                        break;
                    case "email":
                        if (!Objects.equals(entry.getValue(), "")) {
                            user.get().setEmail(entry.getValue());
                        }
                        break;
                    case "password":
                        if (!Objects.equals(entry.getValue(), "")) {
                            user.get().setPassword(entry.getValue());
                        }
                        break;
                }
            }

            try {
                return userRepository.save(user.get()).getId();
            } catch (Exception e) {
                throw new UserUpdateException();
            }
        }

        throw new UserNotFoundException();
    }

    public UUID deleteUser(UUID userId)
            throws UserNotFoundException {

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            userRepository.deleteById(userId);

            return userId;
        }

        throw new UserNotFoundException();
    }

    public UUID getUserId(LoginUserRequest loginUserRequest)
            throws UserNotFoundException {

        String username = loginUserRequest.getUsername();
        String password = loginUserRequest.getPassword();

        Optional<UUID> userId = userRepository.findByUsernameAndPassword(username, password);

        if (userId.isPresent()) {
            return userId.get();
        }

        throw new UserNotFoundException();
    }
}
