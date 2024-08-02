package com.praveenukkoji.userservice.dto.request.user;

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
    private String fullname;
    private String username;
    private String email;
    private String password;
    private UUID roleId;
}
