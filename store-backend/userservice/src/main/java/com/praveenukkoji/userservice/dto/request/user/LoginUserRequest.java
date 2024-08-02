package com.praveenukkoji.userservice.dto.request.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUserRequest {
    @NotNull(message = "username is null")
    @NotEmpty(message = "username is empty")
    private String username;

    @NotNull(message = "password is null")
    @NotEmpty(message = "password is empty")
    private String password;
}
