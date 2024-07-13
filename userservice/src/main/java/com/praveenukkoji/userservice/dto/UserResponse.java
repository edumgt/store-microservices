package com.praveenukkoji.userservice.dto;

import com.praveenukkoji.userservice.dto.response.GetUserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private GetUserResponse payload;
    private String message;
}
