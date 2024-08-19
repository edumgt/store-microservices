package com.praveenukkoji.userservice.controller;

import com.praveenukkoji.userservice.dto.error.ValidationResponse;
import com.praveenukkoji.userservice.dto.request.address.CreateAddressRequest;
import com.praveenukkoji.userservice.exception.address.AddressCreateException;
import com.praveenukkoji.userservice.exception.address.AddressNotFoundException;
import com.praveenukkoji.userservice.exception.address.AddressUpdateException;
import com.praveenukkoji.userservice.exception.address.DeleteAddressException;
import com.praveenukkoji.userservice.exception.user.UserNotFoundException;
import com.praveenukkoji.userservice.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping(path = "")
    public ResponseEntity<?> createAddress(
            @RequestParam(defaultValue = "", name = "userId") String userId,
            @RequestBody @Valid CreateAddressRequest createAddressRequest
    ) throws UserNotFoundException, AddressCreateException {
        if (Objects.equals(userId, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("userId", "user id is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(userId);
        return ResponseEntity.status(201).body(addressService.createAddress(id, createAddressRequest));
    }

    @GetMapping(path = "")
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

    @PatchMapping(path = "/set-default")
    public ResponseEntity<?> setAddressDefault(
            @RequestParam(defaultValue = "", name = "userId") String userId,
            @RequestParam(defaultValue = "", name = "addressId") String addressId
    ) throws UserNotFoundException, AddressNotFoundException, AddressUpdateException {
        if (Objects.equals(userId, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("userId", "user id is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        if (Objects.equals(addressId, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("addressId", "address id is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        UUID uId = UUID.fromString(userId);
        UUID aId = UUID.fromString(addressId);
        return ResponseEntity.status(200).body(addressService.setAddressDefault(uId, aId));
    }

    @DeleteMapping(path = "")
    public ResponseEntity<?> deleteAddress(
            @RequestParam(defaultValue = "", name = "addressId") String addressId
    ) throws AddressNotFoundException, UserNotFoundException, DeleteAddressException {
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
}
