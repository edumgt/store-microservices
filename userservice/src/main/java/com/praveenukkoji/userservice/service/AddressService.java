package com.praveenukkoji.userservice.service;

import com.praveenukkoji.userservice.dto.Response;
import com.praveenukkoji.userservice.exception.AddressNotFoundException;
import com.praveenukkoji.userservice.exception.AddressUpdateException;
import com.praveenukkoji.userservice.exception.UserNotFoundException;
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

    public Address addAddress(UUID userId, Address addressEntity)
            throws UserNotFoundException {

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            addressEntity.setIsDefault(false);
            addressEntity.setUser(user.get());

            Address address = addressRepository.saveAndFlush(addressEntity);

            return address;
        } else {
            throw new UserNotFoundException();
        }
    }

    public List<Address> getAddress(UUID userId) throws UserNotFoundException {

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            return user.get().getAddressList();
        } else {
            throw new UserNotFoundException();
        }
    }

    public Response deleteAddress(UUID addressId) throws AddressNotFoundException {

        Optional<Address> address = addressRepository.findById(addressId);

        if (address.isPresent()) {
            addressRepository.deleteById(addressId);
            return Response.builder()
                    .message("address deleted with id = " + addressId)
                    .build();
        } else {
            throw new AddressNotFoundException();
        }
    }

    @Transactional
    public Address updateAddress(UUID addressId, Map<String, String> updates)
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
                            address.get().setAddressPincode(entry.getValue());
                        }
                        break;
                }
            }

            try {

                Address updatedAddress = addressRepository.saveAndFlush(address.get());

                Address payload = Address.builder()
                        .addressId(updatedAddress.getAddressId())
                        .addressLine(updatedAddress.getAddressLine())
                        .addressCountry(updatedAddress.getAddressCountry())
                        .addressState(updatedAddress.getAddressState())
                        .addressCity(updatedAddress.getAddressCity())
                        .addressPincode(updatedAddress.getAddressPincode())
                        .build();

                return payload;
            } catch (Exception e) {
                throw new AddressUpdateException();
            }

        } else {
            throw new AddressNotFoundException();
        }
    }
}
