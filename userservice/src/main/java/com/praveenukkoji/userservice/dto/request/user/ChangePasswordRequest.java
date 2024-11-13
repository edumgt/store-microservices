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
public class ChangePasswordRequest {
    @NotNull(message = "user id is null")
    @NotEmpty(message = "user id is empty")
    private String userId;

    @NotNull(message = "password is null")
    @NotEmpty(message = "password is empty")
    private String password;
}