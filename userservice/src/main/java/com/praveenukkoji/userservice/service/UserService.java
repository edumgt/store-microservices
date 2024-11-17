package com.praveenukkoji.userservice.service;

import com.praveenukkoji.userservice.dto.user.request.ChangePasswordRequest;
import com.praveenukkoji.userservice.dto.user.request.CreateUserRequest;
import com.praveenukkoji.userservice.dto.role.response.RoleResponse;
import com.praveenukkoji.userservice.dto.user.request.UpdateActiveStatusRequest;
import com.praveenukkoji.userservice.dto.user.request.UpdateUserRequest;
import com.praveenukkoji.userservice.dto.user.response.UserResponse;
import com.praveenukkoji.userservice.exception.error.ValidationException;
import com.praveenukkoji.userservice.exception.role.RoleNotFoundException;
import com.praveenukkoji.userservice.exception.user.*;
import com.praveenukkoji.userservice.model.Role;
import com.praveenukkoji.userservice.model.User;
import com.praveenukkoji.userservice.repository.RoleRepository;
import com.praveenukkoji.userservice.repository.UserRepository;
import com.praveenukkoji.userservice.utility.UserUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserUtility userUtility;

    // create
    public UUID createUser(CreateUserRequest createUserRequest)
            throws RoleNotFoundException, UserCreateException, PasswordEncryptionException, ValidationException {

        log.info("creating new user = {}", createUserRequest);

        String fullname = createUserRequest.getFullname();
        String username = createUserRequest.getUsername();
        String password = createUserRequest.getPassword();
        String email = createUserRequest.getEmail();
        String id = createUserRequest.getRoleId();

        if(Objects.equals(id, "")) {
            throw new ValidationException("roleId", "role id is empty");
        }
        if(Objects.equals(fullname, "")) {
            throw new ValidationException("fullname", "fullname is empty");
        }
        if(Objects.equals(username, "")) {
            throw new ValidationException("username", "username is empty");
        }

        if(Objects.equals(password, "")) {
            throw new ValidationException("password", "password is empty");
        }
        if (password.length() < 8) {
            throw new ValidationException("password", "password must be at least 8 characters");
        }

        if(Objects.equals(email, "")) {
            throw new ValidationException("email", "email is empty");
        }

        try {
            UUID roleId = UUID.fromString(id);
            Optional<Role> role = roleRepository.findById(roleId);

            if (role.isPresent()) {
                String encryptedPassword = userUtility.getEncryptedPassword(password);

                User newUser = User.builder()
                        .fullname(createUserRequest.getFullname())
                        .username(createUserRequest.getUsername())
                        .password(encryptedPassword)
                        .email(createUserRequest.getEmail())
                        .isActive(true)
                        .role(role.get())
                        .build();

                return userRepository.save(newUser).getId();
            } else {
                throw new RoleNotFoundException("role with id = " + id + " not found");
            }
        }
        catch (PasswordEncryptionException e) {
            throw new PasswordEncryptionException(e.getMessage());
        }
        catch (RoleNotFoundException e) {
            throw new RoleNotFoundException(e.getMessage());
        }
        catch (DataIntegrityViolationException e) {
            throw new UserCreateException(e.getMostSpecificCause().getMessage());
        }
        catch (Exception e) {
            throw new UserCreateException(e.getMessage());
        }
    }

    // get
    public UserResponse getUser(String id) throws UserNotFoundException, ValidationException {

        log.info("fetching user having id = {}", id);

        if(id.isEmpty()) {
            throw new ValidationException("userId", "user id is empty");
        }

        UUID userId = UUID.fromString(id);

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            return UserResponse.builder()
                    .id(user.get().getId())
                    .fullname(user.get().getFullname())
                    .username(user.get().getUsername())
                    .email(user.get().getEmail())
                    .roleType(user.get().getRole().getType())
                    .build();
        }

        throw new UserNotFoundException("user with id = " + id + " not found");
    }

    //update
    public void updateUser(UpdateUserRequest updateUserRequest)
            throws UserUpdateException, UserNotFoundException, ValidationException {

        String id = updateUserRequest.getUserId();
        String username = updateUserRequest.getUsername();
        String fullname = updateUserRequest.getFullname();
        String email = updateUserRequest.getEmail();

        log.info("updating user having id = {}", id);

        if(id.isEmpty()) {
            throw new ValidationException("userId", "user id is empty");
        }

        try {
            UUID userId = UUID.fromString(id);
            Optional<User> user = userRepository.findById(userId);

            if (user.isPresent()) {
                User updatedUser = user.get();

                if(!Objects.equals(username, "")) {
                    updatedUser.setUsername(username);
                }
                if(!Objects.equals(fullname, "")) {
                    updatedUser.setFullname(fullname);
                }
                if(!Objects.equals(email, "")) {
                    updatedUser.setEmail(email);
                }

                userRepository.save(updatedUser);
            }
            else {
                throw new UserNotFoundException("user with id = " + id + " not found");
            }
        }
        catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        }
        catch (Exception e) {
            throw new UserUpdateException(e.getMessage());
        }
    }

    // delete
    public void deleteUser(String id)
            throws UserNotFoundException, UserDeleteException, ValidationException {

        log.info("deleting user having id = {}", id);

        if(id.isEmpty()) {
            throw new ValidationException("userId", "user id is empty");
        }

        try {
            UUID userId = UUID.fromString(id);
            Optional<User> user = userRepository.findById(userId);

            if (user.isPresent()) {
                    userRepository.deleteById(userId);
            }
            else {
                throw new UserNotFoundException("user with id = " + id + " not found");
            }
        }
        catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        }
        catch (Exception e) {
            throw new UserDeleteException(e.getMessage());
        }
    }

    // change password
    public void changePassword(ChangePasswordRequest changePasswordRequest)
            throws UserNotFoundException, UserUpdateException, PasswordEncryptionException, ValidationException {

        String id = changePasswordRequest.getUserId();
        String password = changePasswordRequest.getPassword();

        log.info("changing password for user having id = {}", id);

        if(id.isEmpty()) {
            throw new ValidationException("userId", "user id is empty");
        }

        if(Objects.equals(password, "")) {
            throw new ValidationException("password", "password is empty");
        }
        if (password.length() < 8) {
            throw new ValidationException("password", "password must be at least 8 characters");
        }

        try {
            UUID userId = UUID.fromString(id);
            Optional<User> user = userRepository.findById(userId);

            if (user.isPresent()) {
                User updatedUser = user.get();

                String encryptedPassword = userUtility.getEncryptedPassword(password);
                updatedUser.setPassword(encryptedPassword);

                userRepository.save(updatedUser);
            } else {
                throw new UserNotFoundException("user with id = " + id + " not found");
            }
        }
        catch (PasswordEncryptionException e) {
            throw new PasswordEncryptionException(e.getMessage());
        }
        catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        }
        catch (Exception e) {
            throw new UserUpdateException(e.getMessage());
        }
    }

    // update active status
    public void updateActiveStatus(UpdateActiveStatusRequest updateActiveStatusRequest)
            throws UserUpdateException, ValidationException, UserNotFoundException {

        String id = updateActiveStatusRequest.getUserId();
        Boolean activeStatus = updateActiveStatusRequest.getActiveStatus();

        log.info("updating active status for user having id = {}", id);

        if(id.isEmpty()) {
            throw new ValidationException("userId", "user id is empty");
        }

        try {
            UUID userId = UUID.fromString(id);
            Optional<User> user = userRepository.findById(userId);

            if (user.isPresent()) {
                User updatedUser = user.get();
                updatedUser.setIsActive(activeStatus);

                userRepository.save(updatedUser);
            }
            else {
                throw new UserNotFoundException("user with id = " + id + " not found");
            }
        }
        catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        }
        catch (Exception e) {
            throw new UserUpdateException(e.getMessage());
        }
    }
}
