package com.praveenukkoji.userservice.dto.request.address;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAddressRequest {
    @NotNull(message = "user id is null")
    private UUID userId;

    @NotNull(message = "address line is null")
    @NotEmpty(message = "address line is empty")
    private String line;

    @NotNull(message = "country is null")
    @NotEmpty(message = "country is empty")
    private String country;

    @NotNull(message = "state is null")
    @NotEmpty(message = "state is empty")
    private String state;

    @NotNull(message = "city is null")
    @NotEmpty(message = "city is empty")
    private String city;

    @NotNull(message = "pincode is null")
    @NotEmpty(message = "pincode is empty")
    private String pincode;
}
