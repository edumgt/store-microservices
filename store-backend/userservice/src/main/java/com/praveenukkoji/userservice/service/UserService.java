package com.praveenukkoji.userservice.service;

import com.praveenukkoji.userservice.dto.request.user.ChangePasswordRequest;
import com.praveenukkoji.userservice.dto.request.user.CreateUserRequest;
import com.praveenukkoji.userservice.dto.response.role.RoleResponse;
import com.praveenukkoji.userservice.dto.response.user.UserResponse;
import com.praveenukkoji.userservice.exception.role.RoleNotFoundException;
import com.praveenukkoji.userservice.exception.user.UserCreateException;
import com.praveenukkoji.userservice.exception.user.UserDeleteException;
import com.praveenukkoji.userservice.exception.user.UserNotFoundException;
import com.praveenukkoji.userservice.exception.user.UserUpdateException;
import com.praveenukkoji.userservice.model.Role;
import com.praveenukkoji.userservice.model.User;
import com.praveenukkoji.userservice.repository.RoleRepository;
import com.praveenukkoji.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    // create
    public UUID createUser(CreateUserRequest createUserRequest)
            throws RoleNotFoundException, UserCreateException {

        UUID roleId = createUserRequest.getRoleId();
        Optional<Role> role = roleRepository.findById(roleId);

        if (role.isPresent()) {
            String password = createUserRequest.getPassword();

            if (password.length() < 8) {
                throw new UserCreateException("Password must be at least 8 characters");
            }

            User newUser = User.builder()
                    .fullname(createUserRequest.getFullname())
                    .username(createUserRequest.getUsername())
                    .password(password)
                    .email(createUserRequest.getEmail())
                    .isActive(true)
                    .role(role.get())
                    .build();

            try {
                return userRepository.save(newUser).getId();
            } catch (DataIntegrityViolationException e) {
                throw new UserCreateException(e.getMostSpecificCause().getMessage());
            } catch (Exception e) {
                throw new UserCreateException(e.getMessage());
            }
        }

        throw new RoleNotFoundException("role with id = " + roleId + " not found");
    }

    // retrieve
    public UserResponse getUser(UUID userId) throws UserNotFoundException {

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            Role userRole = user.get().getRole();
            RoleResponse role = RoleResponse.builder()
                    .id(userRole.getId())
                    .type(userRole.getType())
                    .build();

            return UserResponse.builder()
                    .id(user.get().getId())
                    .fullname(user.get().getFullname())
                    .username(user.get().getUsername())
                    .email(user.get().getEmail())
                    .role(role)
                    .build();
        }

        throw new UserNotFoundException("user with id = " + userId + " not found");
    }

    //update
    // TODO: change Map<String, String> to Class of UpdateUserRequest
    public UUID updateUser(UUID userId, Map<String, String> updates)
            throws UserUpdateException, UserNotFoundException {

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            User updatedUser = user.get();

            for (Map.Entry<String, String> entry : updates.entrySet()) {
                switch (entry.getKey()) {
                    case "fullname":
                        if (!Objects.equals(entry.getValue(), "")) {
                            updatedUser.setFullname(entry.getValue());
                        }
                        break;
                    case "username":
                        if (!Objects.equals(entry.getValue(), "")) {
                            updatedUser.setUsername(entry.getValue());
                        }
                        break;
                    case "email":
                        if (!Objects.equals(entry.getValue(), "")) {
                            updatedUser.setEmail(entry.getValue());
                        }
                        break;
                }
            }

            try {
                return userRepository.save(updatedUser).getId();
            } catch (Exception e) {
                throw new UserUpdateException(e.getMessage());
            }
        }

        throw new UserNotFoundException("user with id = " + userId + " not found");
    }

    // delete
    public void deleteUser(UUID userId)
            throws UserNotFoundException, UserDeleteException {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            try {
                userRepository.deleteById(userId);
                return;
            } catch (Exception e) {
                throw new UserDeleteException(e.getMessage());
            }
        }

        throw new UserNotFoundException("user with id = " + userId + " not found");
    }

    // change password
    public UUID changePassword(UUID userId, ChangePasswordRequest changePasswordRequest)
            throws UserNotFoundException, UserUpdateException {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            User updatedUser = user.get();

            String newPassword = changePasswordRequest.getPassword();

            if (newPassword.length() < 8) {
                throw new UserUpdateException("password should be at least 8 characters");
            }

            updatedUser.setPassword(newPassword);

            try {
                return userRepository.save(updatedUser).getId();
            } catch (Exception e) {
                throw new UserUpdateException(e.getMessage());
            }
        }

        throw new UserNotFoundException("user with id = " + userId + " not found");
    }

    // update active status
    public UUID updateActiveStatus(UUID userId, boolean status) throws UserUpdateException {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            User updatedUser = user.get();

            updatedUser.setIsActive(status);

            return userRepository.save(updatedUser).getId();
        }

        throw new UserUpdateException("user with id = " + userId + " not found");
    }
}
