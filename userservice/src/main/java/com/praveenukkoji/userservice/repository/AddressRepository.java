package com.praveenukkoji.userservice.repository;

import com.praveenukkoji.userservice.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
