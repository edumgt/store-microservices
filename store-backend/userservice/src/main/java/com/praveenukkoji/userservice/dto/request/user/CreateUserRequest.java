package com.praveenukkoji.userservice.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {
    @NotNull(message = "fullname is null")
    @NotEmpty(message = "fullname is empty")
    private String fullname;

    @NotNull(message = "username is null")
    @NotEmpty(message = "username is empty")
    private String username;

    @NotNull(message = "email is null")
    @NotEmpty(message = "email is empty")
    @Email(message = "email format invalid")
    private String email;

    @NotNull(message = "password is null")
    @NotEmpty(message = "password is empty")
    private String password;

    @NotNull(message = "roleId is null")
    private UUID roleId;
}
