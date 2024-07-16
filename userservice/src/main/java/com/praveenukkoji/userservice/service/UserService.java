package com.praveenukkoji.userservice.service;

import com.praveenukkoji.userservice.dto.Response;
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

import java.time.LocalDateTime;
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

    public UserResponse createUser(CreateUserRequest createUserRequest)
            throws UserCreateException, RoleNotFoundException {

        UUID roleId = createUserRequest.getRoleId();
        Optional<Role> role = roleRepository.findById(roleId);

        if (role.isPresent()) {
            User user = User.builder()
                    .userFullname(createUserRequest.getFullname())
                    .userUsername(createUserRequest.getUsername())
                    .userPassword(createUserRequest.getPassword())
                    .userEmail(createUserRequest.getEmail())
                    .isActive(true)
                    .createdOn(LocalDateTime.now())
                    .role(role.get())
                    .build();

            try {
                user = userRepository.saveAndFlush(user);

                log.info("user created with userId = {}", user.getUserId());

                return UserResponse.builder()
                        .userId(user.getUserId())
                        .fullname(user.getUserFullname())
                        .username(user.getUserUsername())
                        .email(user.getUserEmail())
                        .isActive(user.getIsActive())
                        .role(user.getRole())
                        .build();
            } catch (Exception e) {
                throw new UserCreateException();
            }
        } else {
            throw new RoleNotFoundException();
        }
    }

    public UserResponse getUser(UUID userId) throws UserNotFoundException {

        Optional<User> userEntity = userRepository.findById(userId);

        if (userEntity.isPresent()) {
            User user = userEntity.get();
            log.info("user fetched with userId = {}", userId);

            return UserResponse.builder()
                    .userId(user.getUserId())
                    .fullname(user.getUserFullname())
                    .username(user.getUserUsername())
                    .email(user.getUserEmail())
                    .isActive(user.getIsActive())
                    .role(user.getRole())
                    .addressList(user.getAddressList())
                    .build();
        }

        throw new UserNotFoundException();
    }

    @Transactional
    public UserResponse updateUser(UUID userId, Map<String, String> updates)
            throws UserNotFoundException, UserUpdateException {

        Optional<User> user = userRepository.findById(userId);

        // fields that can be updated fullname, username, email, password
        if (user.isPresent()) {

            for (Map.Entry<String, String> entry : updates.entrySet()) {
                switch (entry.getKey()) {
                    case "fullname":
                        if (!Objects.equals(entry.getValue(), "")) {
                            user.get().setUserFullname(entry.getValue());
                        }
                        break;
                    case "username":
                        if (!Objects.equals(entry.getValue(), "")) {
                            user.get().setUserUsername(entry.getValue());
                        }
                        break;
                    case "email":
                        if (!Objects.equals(entry.getValue(), "")) {
                            user.get().setUserEmail(entry.getValue());
                        }
                        break;
                    case "password":
                        if (!Objects.equals(entry.getValue(), "")) {
                            user.get().setUserPassword(entry.getValue());
                        }
                        break;
                }
            }

            try {
                user.get().setModifiedOn(LocalDateTime.now());
                user.get().setModifiedBy(user.get().getUserId());

                User updatedUser = user.get();
                updatedUser = userRepository.saveAndFlush(updatedUser);

                return UserResponse.builder()
                        .userId(updatedUser.getUserId())
                        .fullname(updatedUser.getUserFullname())
                        .username(updatedUser.getUserUsername())
                        .email(updatedUser.getUserEmail())
                        .isActive(updatedUser.getIsActive())
                        .role(updatedUser.getRole())
                        .addressList(updatedUser.getAddressList())
                        .build();
            } catch (Exception e) {
                throw new UserUpdateException();
            }

        } else {
            throw new UserNotFoundException();
        }
    }

    public Response deleteUser(UUID userId) throws UserNotFoundException {

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            userRepository.deleteById(userId);
            log.info("user deleted with userId = {}", userId);

            return Response.builder()
                    .message("user deleted with userId = " + userId)
                    .build();
        }

        throw new UserNotFoundException();
    }

    public String getUserId(LoginUserRequest loginUserRequest) throws UserNotFoundException {
        String email = loginUserRequest.getEmail();
        String password = loginUserRequest.getPassword();

        Optional<UUID> userId = userRepository.findByEmailAndPassword(email, password);

        if (userId.isPresent()) {
            return userId.get().toString();
        } else {
            throw new UserNotFoundException();
        }
    }
}
