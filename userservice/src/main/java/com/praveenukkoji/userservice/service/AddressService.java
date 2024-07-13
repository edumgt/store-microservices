package com.praveenukkoji.userservice.service;

import com.praveenukkoji.userservice.dto.AddressResponse;
import com.praveenukkoji.userservice.exception.AddressNotFoundException;
import com.praveenukkoji.userservice.exception.UserNotFoundException;
import com.praveenukkoji.userservice.model.Address;
import com.praveenukkoji.userservice.model.User;
import com.praveenukkoji.userservice.repository.AddressRepository;
import com.praveenukkoji.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    public Address addAddress(UUID userId, Address addressEntity) throws UserNotFoundException {

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            addressEntity.setIsDefault(false);
            addressEntity.setUser(user.get());

            Address address = addressRepository.saveAndFlush(addressEntity);

            return address;
        } else {
            throw new UserNotFoundException("user not found");
        }
    }

    public List<Address> getAddress(UUID userId) throws UserNotFoundException {

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            return user.get().getAddressList();
        } else {
            throw new UserNotFoundException("user not found");
        }
    }

    public AddressResponse deleteAddress(UUID addressId) throws AddressNotFoundException {

        Optional<Address> address = addressRepository.findById(addressId);

        if (address.isPresent()) {
            addressRepository.deleteById(addressId);
            return AddressResponse.builder()
                    .message("address deleted with id = " + addressId)
                    .build();
        } else {
            throw new AddressNotFoundException("address not found");
        }
    }
}
