package com.praveenukkoji.userservice.dto.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {
    @NotNull(message = "user id is null")
    private String userId;

    @NotNull(message = "fullname is null")
    private String fullname;

    @NotNull(message = "username is null")
    private String username;

    @NotNull(message = "email is null")
    @Email(message = "email format invalid")
    private String email;
}
