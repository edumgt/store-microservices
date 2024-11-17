package com.praveenukkoji.userservice.repository;

import com.praveenukkoji.userservice.model.Address;
import com.praveenukkoji.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

    // get address list by user
    @Query("""
            SELECT a FROM Address a WHERE a.user = ?1
            """)
    List<Address> findAllAddressByUser(User user);
}
