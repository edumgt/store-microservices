package com.praveenukkoji.userservice.dto.response.user;

import com.praveenukkoji.userservice.model.Address;
import com.praveenukkoji.userservice.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private UUID userId;
    private String fullname;
    private String username;
    private String email;
    private Boolean isActive;
    private Role role;
    private List<Address> addressList;
}
