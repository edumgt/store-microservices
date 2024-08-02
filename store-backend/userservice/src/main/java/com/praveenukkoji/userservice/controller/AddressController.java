package com.praveenukkoji.userservice.controller;

import com.praveenukkoji.userservice.dto.error.ErrorResponse;
import com.praveenukkoji.userservice.dto.request.address.AddAddressRequest;
import com.praveenukkoji.userservice.exception.address.AddressCreateException;
import com.praveenukkoji.userservice.exception.address.AddressNotFoundException;
import com.praveenukkoji.userservice.exception.address.AddressUpdateException;
import com.praveenukkoji.userservice.exception.user.UserNotFoundException;
import com.praveenukkoji.userservice.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping(path = "/add")
    public ResponseEntity<?> addAddress(
            @RequestParam(defaultValue = "", name = "userId") String userId,
            @RequestBody @Valid AddAddressRequest addAddressRequest
    ) throws UserNotFoundException, AddressCreateException {

        if (Objects.equals(userId, "")) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .message("user id is empty")
                    .build();
            return ResponseEntity.status(400).body(errorResponse);
        }

        UUID id = UUID.fromString(userId);
        return ResponseEntity.status(201).body(addressService.addAddress(id, addAddressRequest));
    }

    @GetMapping(path = "/get")
    public ResponseEntity<?> getAddress(
            @RequestParam(defaultValue = "", name = "userId") String userId
    ) throws UserNotFoundException {

        if (Objects.equals(userId, "")) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .message("user id is empty")
                    .build();
            return ResponseEntity.status(400).body(errorResponse);
        }

        UUID id = UUID.fromString(userId);
        return ResponseEntity.status(200).body(addressService.getAddress(id));
    }

    @PatchMapping(path = "/update")
    public ResponseEntity<?> updateAddress(
            @RequestParam(defaultValue = "", name = "addressId") String addressId,
            @RequestBody Map<String, String> updates
    ) throws AddressNotFoundException, AddressUpdateException {

        if (Objects.equals(addressId, "")) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .message("address id is empty")
                    .build();
            return ResponseEntity.status(400).body(errorResponse);
        }

        UUID id = UUID.fromString(addressId);
        return ResponseEntity.status(200).body(addressService.updateAddress(id, updates));
    }

    @PatchMapping(path = "/set-default")
    public ResponseEntity<?> setAddressDefault(
            @RequestParam(defaultValue = "", name = "userId") String userId,
            @RequestParam(defaultValue = "", name = "addressId") String addressId
    ) throws UserNotFoundException, AddressNotFoundException, AddressUpdateException {

        if (Objects.equals(userId, "")) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .message("user id is empty")
                    .build();
            return ResponseEntity.status(400).body(errorResponse);
        }

        if (Objects.equals(addressId, "")) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .message("address id is empty")
                    .build();
            return ResponseEntity.status(400).body(errorResponse);
        }

        UUID uId = UUID.fromString(userId);
        UUID aId = UUID.fromString(addressId);
        return ResponseEntity.status(200).body(addressService.setAddressDefault(uId, aId));
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> deleteAddress(
            @RequestParam(defaultValue = "", name = "addressId") String addressId
    ) throws AddressNotFoundException {

        if (Objects.equals(addressId, "")) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .message("address id is empty")
                    .build();
            return ResponseEntity.status(400).body(errorResponse);
        }

        UUID id = UUID.fromString(addressId);
        return ResponseEntity.status(200).body(addressService.deleteAddress(id));
    }
}
