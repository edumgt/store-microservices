package com.praveenukkoji.userservice.dto.role.request;

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
public class UpdateRoleRequest {
    @NotNull(message = "role id is null")
    private String roleId;

    @NotNull(message = "role type is null")
    private String type;
}
