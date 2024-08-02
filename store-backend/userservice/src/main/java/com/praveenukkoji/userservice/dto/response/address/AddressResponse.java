package com.praveenukkoji.userservice.dto.response.address;

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
    private UUID addressId;
    private String addressLine;
    private String country;
    private String state;
    private String city;
    private Integer pincode;
    private Boolean isDefault;
}
