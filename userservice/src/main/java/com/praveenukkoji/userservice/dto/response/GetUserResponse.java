package com.praveenukkoji.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetUserResponse {
    private UUID userId;
    private String username;
    private String email;
    private LocalDate createdOn;
    private UUID createdBy;
    private UUID modifiedBy;
}
