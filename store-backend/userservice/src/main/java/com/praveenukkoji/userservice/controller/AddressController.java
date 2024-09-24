package com.praveenukkoji.userservice.controller;

import com.praveenukkoji.userservice.dto.error.ValidationResponse;
import com.praveenukkoji.userservice.dto.request.address.CreateAddressRequest;
import com.praveenukkoji.userservice.exception.address.AddressCreateException;
import com.praveenukkoji.userservice.exception.address.AddressDeleteException;
import com.praveenukkoji.userservice.exception.address.AddressNotFoundException;
import com.praveenukkoji.userservice.exception.address.AddressUpdateException;
import com.praveenukkoji.userservice.exception.user.UserNotFoundException;
import com.praveenukkoji.userservice.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/addresses")
public class AddressController {

    private final AddressService addressService;

    // create
    @PostMapping(path = "")
    public ResponseEntity<?> createAddress(@RequestBody @Valid CreateAddressRequest createAddressRequest)
            throws UserNotFoundException, AddressCreateException {
        return ResponseEntity.status(201).body(addressService.createAddress(createAddressRequest));
    }

    // retrieve
    @GetMapping(path = "")
    public ResponseEntity<?> getAddress(
            @RequestParam(defaultValue = "", name = "addressId") String addressId
    ) throws AddressNotFoundException {
        if (Objects.equals(addressId, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("addressId", "address id is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(addressId);
        return ResponseEntity.status(200).body(addressService.getAddress(id));
    }

    // update
    @PatchMapping(path = "")
    public ResponseEntity<?> updateAddress(
            @RequestParam(defaultValue = "", name = "addressId") String addressId,
            @RequestBody Map<String, String> updates
    ) throws AddressNotFoundException, AddressUpdateException {
        if (Objects.equals(addressId, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("addressId", "address id is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(addressId);
        return ResponseEntity.status(200).body(addressService.updateAddress(id, updates));
    }

    // delete
    @DeleteMapping(path = "")
    public ResponseEntity<?> deleteAddress(
            @RequestParam(defaultValue = "", name = "addressId") String addressId
    ) throws AddressNotFoundException, AddressDeleteException {
        if (Objects.equals(addressId, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("addressId", "address id is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(addressId);
        addressService.deleteAddress(id);

        return ResponseEntity.status(204).body("");
    }

    // get address by user
    @GetMapping("/get-by-user")
    public ResponseEntity<?> getAddressByUser(
            @RequestParam(defaultValue = "", name = "userId") String userId
    ) throws UserNotFoundException {
        if (Objects.equals(userId, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("userId", "user id is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(userId);
        return ResponseEntity.status(200).body(addressService.getAddressByUser(id));
    }
}
