package com.praveenukkoji.userservice.dto.role.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponse {
    private UUID id;
    private String type;
}
