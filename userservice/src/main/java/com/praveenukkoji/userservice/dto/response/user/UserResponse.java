package com.praveenukkoji.userservice.dto.response.user;

import com.praveenukkoji.userservice.dto.response.role.RoleResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private UUID id;
    private String fullname;
    private String username;
    private String email;
    private RoleResponse role;
}
