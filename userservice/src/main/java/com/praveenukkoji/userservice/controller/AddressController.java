package com.praveenukkoji.userservice.controller;

import com.praveenukkoji.userservice.dto.address.request.CreateAddressRequest;
import com.praveenukkoji.userservice.dto.address.request.UpdateAddressRequest;
import com.praveenukkoji.userservice.exception.address.AddressCreateException;
import com.praveenukkoji.userservice.exception.address.AddressDeleteException;
import com.praveenukkoji.userservice.exception.address.AddressNotFoundException;
import com.praveenukkoji.userservice.exception.address.AddressUpdateException;
import com.praveenukkoji.userservice.exception.error.ValidationException;
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
            throws UserNotFoundException, AddressCreateException, ValidationException {
        return ResponseEntity.status(201).body(addressService.createAddress(createAddressRequest));
    }

    // get
    @GetMapping(path = "")
    public ResponseEntity<?> getAddress(
            @RequestParam(defaultValue = "", name = "addressId") String addressId
    ) throws AddressNotFoundException, ValidationException {
        return ResponseEntity.status(200).body(addressService.getAddress(addressId));
    }

    // update
    @PatchMapping(path = "")
    public ResponseEntity<?> updateAddress(
            @RequestBody @Valid UpdateAddressRequest updateAddressRequest
    ) throws AddressNotFoundException, AddressUpdateException, ValidationException {
        addressService.updateAddress(updateAddressRequest);
        return ResponseEntity.status(204).body("");
    }

    // delete
    @DeleteMapping(path = "")
    public ResponseEntity<?> deleteAddress(
            @RequestParam(defaultValue = "", name = "addressId") String addressId
    ) throws AddressNotFoundException, AddressDeleteException, ValidationException {
        addressService.deleteAddress(addressId);
        return ResponseEntity.status(204).body("");
    }

    // get address by user
    @GetMapping("/get-by-user")
    public ResponseEntity<?> getAddressByUser(
            @RequestParam(defaultValue = "", name = "userId") String userId
    ) throws UserNotFoundException, ValidationException {
        return ResponseEntity.status(200).body(addressService.getAddressByUser(userId));
    }
}
