package com.praveenukkoji.userservice.dto.address.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAddressRequest {
    @NotNull(message = "address id is null")
    private String addressId;

    @NotNull(message = "address line is null")
    private String line;

    @NotNull(message = "country is null")
    private String country;

    @NotNull(message = "state is null")
    private String state;

    @NotNull(message = "city is null")
    private String city;

    @NotNull(message = "pincode is null")
    private String pincode;
}
