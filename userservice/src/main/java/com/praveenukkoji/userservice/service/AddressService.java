package com.praveenukkoji.userservice.service;

import com.praveenukkoji.userservice.dto.Response;
import com.praveenukkoji.userservice.dto.request.address.AddAddressRequest;
import com.praveenukkoji.userservice.dto.response.address.AddressResponse;
import com.praveenukkoji.userservice.exception.address.AddressCreateException;
import com.praveenukkoji.userservice.exception.address.AddressNotFoundException;
import com.praveenukkoji.userservice.exception.address.AddressUpdateException;
import com.praveenukkoji.userservice.exception.user.UserNotFoundException;
import com.praveenukkoji.userservice.model.Address;
import com.praveenukkoji.userservice.model.User;
import com.praveenukkoji.userservice.repository.AddressRepository;
import com.praveenukkoji.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    public AddressResponse addAddress(UUID userId, AddAddressRequest addAddressRequest)
            throws UserNotFoundException, AddressCreateException {

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            Address newAddress = Address.builder()
                    .addressLine(addAddressRequest.getAddressLine())
                    .addressCountry(addAddressRequest.getCountry())
                    .addressState(addAddressRequest.getState())
                    .addressCity(addAddressRequest.getCity())
                    .addressPincode(addAddressRequest.getPincode())
                    .isDefault(addAddressRequest.getIsDefault())
                    .user(user.get())
                    .build();

            try {
                if (!user.get().getAddressList().isEmpty() && newAddress.getIsDefault()) {
                    List<Address> addressList = user.get().getAddressList().stream()
                            .map(entity -> {
                                entity.setIsDefault(false);
                                return entity;
                            })
                            .toList();

                    addressRepository.saveAll(addressList);
                }

                Address address = addressRepository.saveAndFlush(newAddress);

                return AddressResponse.builder()
                        .addressId(address.getAddressId())
                        .addressLine(address.getAddressLine())
                        .country(address.getAddressCountry())
                        .state(address.getAddressState())
                        .city(address.getAddressCity())
                        .pincode(address.getAddressPincode())
                        .isDefault(address.getIsDefault())
                        .build();
            } catch (Exception e) {
                throw new AddressCreateException();
            }
        }

        throw new UserNotFoundException();
    }

    public List<AddressResponse> getAddress(UUID userId)
            throws UserNotFoundException {

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            List<Address> addressList = user.get().getAddressList();

            return addressList.stream()
                    .map(address -> {
                        return AddressResponse.builder()
                                .addressId(address.getAddressId())
                                .addressLine(address.getAddressLine())
                                .country(address.getAddressCountry())
                                .state(address.getAddressState())
                                .city(address.getAddressCity())
                                .pincode(address.getAddressPincode())
                                .isDefault(address.getIsDefault())
                                .build();
                    }).toList();
        }

        throw new UserNotFoundException();

    }

    public Response deleteAddress(UUID addressId)
            throws AddressNotFoundException {

        Optional<Address> address = addressRepository.findById(addressId);

        if (address.isPresent()) {
            addressRepository.deleteById(addressId);

            return Response.builder()
                    .message("deleted address with id = " + addressId)
                    .build();
        }

        throw new AddressNotFoundException();
    }

    @Transactional
    public AddressResponse updateAddress(UUID addressId, Map<String, String> updates)
            throws AddressNotFoundException, AddressUpdateException {

        Optional<Address> address = addressRepository.findById(addressId);

        if (address.isPresent()) {

            for (Map.Entry<String, String> entry : updates.entrySet()) {
                switch (entry.getKey()) {
                    case "addressLine":
                        if (!Objects.equals(entry.getValue(), "")) {
                            address.get().setAddressLine(entry.getValue());
                        }
                        break;
                    case "country":
                        if (!Objects.equals(entry.getValue(), "")) {
                            address.get().setAddressCountry(entry.getValue());
                        }
                        break;
                    case "state":
                        if (!Objects.equals(entry.getValue(), "")) {
                            address.get().setAddressState(entry.getValue());
                        }
                        break;
                    case "city":
                        if (!Objects.equals(entry.getValue(), "")) {
                            address.get().setAddressCity(entry.getValue());
                        }
                        break;
                    case "pincode":
                        if (!Objects.equals(entry.getValue(), "")) {
                            address.get().setAddressPincode(Integer.valueOf(entry.getValue()));
                        }
                        break;
                }
            }

            try {

                Address updatedAddress = addressRepository.saveAndFlush(address.get());

                return AddressResponse.builder()
                        .addressId(updatedAddress.getAddressId())
                        .addressLine(updatedAddress.getAddressLine())
                        .country(updatedAddress.getAddressCountry())
                        .state(updatedAddress.getAddressState())
                        .city(updatedAddress.getAddressCity())
                        .pincode(updatedAddress.getAddressPincode())
                        .isDefault(updatedAddress.getIsDefault())
                        .build();
            } catch (Exception e) {
                throw new AddressUpdateException();
            }
        }

        throw new AddressNotFoundException();
    }

    @Transactional
    public AddressResponse setAddressDefault(UUID userId, UUID addressId)
            throws UserNotFoundException, AddressNotFoundException, AddressUpdateException {

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            Optional<Address> address = addressRepository.findById(addressId);

            if (address.isPresent()) {

                List<Address> addressList = user.get().getAddressList();

                for (Address entity : addressList) {
                    entity.setIsDefault(entity.getAddressId() == addressId);
                }

                try {
                    List<Address> updatedAddressList = addressRepository.saveAllAndFlush(addressList);

                    Optional<Address> response = updatedAddressList
                            .stream().filter(entity -> {
                                return entity.getAddressId().equals(addressId);
                            }).findFirst();

                    if (response.isPresent())
                        return AddressResponse.builder()
                                .addressId(response.get().getAddressId())
                                .addressLine(response.get().getAddressLine())
                                .country(response.get().getAddressCountry())
                                .state(response.get().getAddressState())
                                .city(response.get().getAddressCity())
                                .pincode(response.get().getAddressPincode())
                                .isDefault(response.get().getIsDefault())
                                .build();
                } catch (Exception e) {
                    throw new AddressUpdateException();
                }
            }

            throw new AddressNotFoundException();
        }

        throw new UserNotFoundException();
    }
}
