package com.praveenukkoji.userservice.dto.user.request;

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
    private String userId;

    @NotNull(message = "password is null")
    private String password;
}
