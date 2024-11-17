package com.praveenukkoji.userservice.dto.user.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateActiveStatusRequest {
    @NotNull(message = "user id is null")
    private String userId;

    @NotNull(message = "active status is null")
    private Boolean activeStatus;
}
