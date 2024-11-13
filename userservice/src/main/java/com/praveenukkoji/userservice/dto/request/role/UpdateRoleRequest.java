package com.praveenukkoji.userservice.dto.request.role;

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
    @NotEmpty(message = "role id is empty")
    private String roleId;

    @NotNull(message = "role type is null")
    @NotEmpty(message = "role type is empty")
    private String type;
}
