package com.praveenukkoji.userservice.service;

import com.praveenukkoji.userservice.dto.address.request.CreateAddressRequest;
import com.praveenukkoji.userservice.dto.address.request.UpdateAddressRequest;
import com.praveenukkoji.userservice.dto.address.response.AddressResponse;
import com.praveenukkoji.userservice.exception.address.AddressCreateException;
import com.praveenukkoji.userservice.exception.address.AddressDeleteException;
import com.praveenukkoji.userservice.exception.address.AddressNotFoundException;
import com.praveenukkoji.userservice.exception.address.AddressUpdateException;
import com.praveenukkoji.userservice.exception.error.ValidationException;
import com.praveenukkoji.userservice.exception.user.UserNotFoundException;
import com.praveenukkoji.userservice.model.Address;
import com.praveenukkoji.userservice.model.User;
import com.praveenukkoji.userservice.repository.AddressRepository;
import com.praveenukkoji.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class AddressService {

    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    // TODO: implement isDefault address logic
    // create
     public UUID createAddress(CreateAddressRequest createAddressRequest)
             throws UserNotFoundException, AddressCreateException, ValidationException {

         String id = createAddressRequest.getUserId();
         String line = createAddressRequest.getLine();
         String country = createAddressRequest.getCountry();
         String state = createAddressRequest.getState();
         String city = createAddressRequest.getCity();
         String pincode = createAddressRequest.getPincode();
         Boolean isDefault = createAddressRequest.getIsDefault();

         log.info("creating new address = {}", createAddressRequest);

         if (id.isEmpty()) {
             throw new ValidationException("userId", "user id is empty");
         }
         if (line.isEmpty()) {
             throw new ValidationException("line", "address line is empty");
         }
         if (country.isEmpty()) {
             throw new ValidationException("country", "country is empty");
         }
         if (state.isEmpty()) {
             throw new ValidationException("state", "state is empty");
         }
         if (city.isEmpty()) {
             throw new ValidationException("city", "city is empty");
         }
         if (pincode.isEmpty()) {
             throw new ValidationException("pincode", "pincode is empty");
         }

         try {

             UUID userId = UUID.fromString(id);
             Optional<User> user = userRepository.findById(userId);

             if (user.isPresent()) {
                 Address newAddress = Address.builder()
                         .line(line)
                         .country(country)
                         .state(state)
                         .city(city)
                         .pincode(pincode)
                         .isDefault(isDefault)
                         .user(user.get())
                         .build();

                 return addressRepository.save(newAddress).getId();
             }
             else {
                 throw new UserNotFoundException("user with id = " + id + " not found");
             }
         }
         catch (UserNotFoundException e) {
             throw new UserNotFoundException(e.getMessage());
         }
         catch (Exception e) {
             throw new AddressCreateException(e.getMessage());
         }
    }

    // get
    public AddressResponse getAddress(String id)
            throws AddressNotFoundException, ValidationException {

        log.info("fetching address having id = {}", id);

        if(id.isEmpty()) {
            throw new ValidationException("addressId", "address id is empty");
        }

        UUID addressId = UUID.fromString(id);
        Optional<Address> address = addressRepository.findById(addressId);

        if (address.isPresent()) {
            return AddressResponse.builder()
                    .id(address.get().getId())
                    .line(address.get().getLine())
                    .country(address.get().getCountry())
                    .state(address.get().getState())
                    .city(address.get().getCity())
                    .pincode(address.get().getPincode())
                    .isDefault(address.get().getIsDefault())
                    .build();
        }

        throw new AddressNotFoundException("address with id = " + id + " not found");
    }

    // update
    public void updateAddress(UpdateAddressRequest updateAddressRequest)
            throws AddressNotFoundException, AddressUpdateException, ValidationException {

        String id = updateAddressRequest.getAddressId();
        String line = updateAddressRequest.getLine();
        String country = updateAddressRequest.getCountry();
        String state = updateAddressRequest.getState();
        String city = updateAddressRequest.getCity();
        String pincode = updateAddressRequest.getPincode();

        log.info("updating address having id = {}", id);

        try {
            UUID addressId = UUID.fromString(id);
            Optional<Address> address = addressRepository.findById(addressId);

            if (address.isPresent()) {
                Address updatedAddress = address.get();

                if(!line.isEmpty()) {
                    updatedAddress.setLine(line);
                }
                if(!country.isEmpty()) {
                    updatedAddress.setCountry(country);
                }
                if(!state.isEmpty()) {
                    updatedAddress.setState(state);
                }
                if(!city.isEmpty()) {
                    updatedAddress.setCity(city);
                }
                if(!pincode.isEmpty()) {
                    updatedAddress.setPincode(pincode);
                }

                addressRepository.save(updatedAddress);
            }
            else {
                throw new AddressNotFoundException("address with id = " + id + " not found");
            }
        }
        catch (AddressNotFoundException e) {
            throw new AddressNotFoundException(e.getMessage());
        }
        catch (Exception e) {
            throw new AddressUpdateException(e.getMessage());
        }
    }

    // delete
    public void deleteAddress(String id)
            throws AddressNotFoundException, AddressDeleteException, ValidationException {

        log.info("deleting address having id = {}", id);

        if(id.isEmpty()) {
            throw new ValidationException("addressId", "address id is empty");
        }

        try {
            UUID addressId = UUID.fromString(id);
            Optional<Address> address = addressRepository.findById(addressId);

            if (address.isPresent()) {
                // if address is default cannot delete it
                if (address.get().getIsDefault()) {
                    throw new AddressDeleteException("cannot delete default address");
                }

                addressRepository.deleteById(addressId);
            }
            else {
                throw new AddressNotFoundException("address with id = " + id + " not found");
            }
        }
        catch (AddressNotFoundException e) {
            throw new AddressNotFoundException(e.getMessage());
        }
        catch (Exception e) {
            throw new AddressDeleteException(e.getMessage());
        }
    }

    // get address by user
    public List<AddressResponse> getAddressByUser(String id)
            throws UserNotFoundException, ValidationException {

        log.info("fetching addresses of user having id =  {}", id);

        if(id.isEmpty()) {
            throw new ValidationException("userId", " user id is empty");
        }

        try {
            UUID userId = UUID.fromString(id);
            Optional<User> user = userRepository.findById(userId);

            if (user.isPresent()) {
                List<Address> addressList = addressRepository.findAllAddressByUser(user.get());

                return addressList.stream().map(address -> AddressResponse.builder()
                                .id(address.getId())
                                .line(address.getLine())
                                .country(address.getCountry())
                                .state(address.getState())
                                .city(address.getCity())
                                .pincode(address.getPincode())
                                .isDefault(address.getIsDefault())
                                .build())
                        .toList();
            }
            else {
                throw new UserNotFoundException("user with id = " + id + " not found");
            }
        }
        catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        }
    }
}
