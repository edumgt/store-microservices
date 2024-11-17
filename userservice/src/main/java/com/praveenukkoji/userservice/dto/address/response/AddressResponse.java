package com.praveenukkoji.userservice.dto.address.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponse {
    private UUID id;
    private String line;
    private String country;
    private String state;
    private String city;
    private String pincode;
    private Boolean isDefault;
}
