package com.praveenukkoji.userservice.repository;

import com.praveenukkoji.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE userEmail=?1 AND userPassword=?2")
    Optional<User> findByEmailAndPassword(String email, String password);
}