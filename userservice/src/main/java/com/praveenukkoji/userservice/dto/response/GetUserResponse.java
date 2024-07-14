package com.praveenukkoji.userservice.dto.response;

import com.praveenukkoji.userservice.model.Address;
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
public class GetUserResponse {
    private UUID userId;
    private String fullname;
    private String username;
    private String email;
    private String roleType;
    private List<Address> addressList;
}
