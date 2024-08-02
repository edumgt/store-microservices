package com.praveenukkoji.userservice.dto.request.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddAddressRequest {
    private String addressLine;
    private String country;
    private String state;
    private String city;
    private Integer pincode;
    private Boolean isDefault;
}
