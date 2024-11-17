package com.praveenukkoji.userservice.dto.address.request;

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
public class CreateAddressRequest {
    @NotNull(message = "user id is null")
    private String userId;

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

    @NotNull(message = "isDefault is null")
    private Boolean isDefault;
}
