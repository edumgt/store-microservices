package com.praveenukkoji.userservice.service;

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

    public UUID addAddress(UUID userId, AddAddressRequest addAddressRequest)
            throws UserNotFoundException, AddressCreateException {

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            Address newAddress = Address.builder()
                    .line(addAddressRequest.getLine())
                    .country(addAddressRequest.getCountry())
                    .state(addAddressRequest.getState())
                    .city(addAddressRequest.getCity())
                    .pincode(addAddressRequest.getPincode())
                    .isDefault(addAddressRequest.getIsDefault())
                    .user(user.get())
                    .build();

            try {

                // only one address should be default
                if (user.get().getAddressList().isEmpty()) {
                    newAddress.setIsDefault(true);
                }

                if (newAddress.getIsDefault()) {
                    List<Address> addressList = user.get().getAddressList().stream()
                            .peek(entity -> entity.setIsDefault(false))
                            .toList();

                    addressRepository.saveAll(addressList);
                }

                return addressRepository.save(newAddress).getId();
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
                                .id(address.getId())
                                .line(address.getLine())
                                .country(address.getCountry())
                                .state(address.getState())
                                .city(address.getCity())
                                .pincode(address.getPincode())
                                .isDefault(address.getIsDefault())
                                .build();
                    }).toList();
        }

        throw new UserNotFoundException();

    }

    public UUID deleteAddress(UUID addressId)
            throws AddressNotFoundException {

        Optional<Address> address = addressRepository.findById(addressId);

        if (address.isPresent()) {
            addressRepository.deleteById(addressId);

            return addressId;
        }

        throw new AddressNotFoundException();
    }

    @Transactional
    public UUID updateAddress(UUID addressId, Map<String, String> updates)
            throws AddressNotFoundException, AddressUpdateException {

        Optional<Address> address = addressRepository.findById(addressId);

        if (address.isPresent()) {

            for (Map.Entry<String, String> entry : updates.entrySet()) {
                switch (entry.getKey()) {
                    case "line":
                        if (!Objects.equals(entry.getValue(), "")) {
                            address.get().setLine(entry.getValue());
                        }
                        break;
                    case "country":
                        if (!Objects.equals(entry.getValue(), "")) {
                            address.get().setCountry(entry.getValue());
                        }
                        break;
                    case "state":
                        if (!Objects.equals(entry.getValue(), "")) {
                            address.get().setState(entry.getValue());
                        }
                        break;
                    case "city":
                        if (!Objects.equals(entry.getValue(), "")) {
                            address.get().setCity(entry.getValue());
                        }
                        break;
                    case "pincode":
                        if (!Objects.equals(entry.getValue(), "")) {
                            address.get().setPincode(entry.getValue());
                        }
                        break;
                }
            }

            try {
                return addressRepository.save(address.get()).getId();
            } catch (Exception e) {
                throw new AddressUpdateException();
            }
        }

        throw new AddressNotFoundException();
    }

    @Transactional
    public UUID setAddressDefault(UUID userId, UUID addressId)
            throws UserNotFoundException, AddressNotFoundException, AddressUpdateException {

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {

            Optional<Address> address = addressRepository.findById(addressId);

            if (address.isPresent()) {

                List<Address> addressList = user.get().getAddressList();

                for (Address entity : addressList) {
                    entity.setIsDefault(entity.getId().equals(addressId));
                }

                try {
                    addressRepository.saveAll(addressList);
                    return addressId;
                } catch (Exception e) {
                    throw new AddressUpdateException();
                }
            }

            throw new AddressNotFoundException();
        }

        throw new UserNotFoundException();
    }
}
