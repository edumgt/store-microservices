package com.praveenukkoji.userservice.repository;

import com.praveenukkoji.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    // finding user by username and password for login
    @Query("""
            SELECT u.id FROM User u
            WHERE u.username=:username AND u.password=:password
            """)
    Optional<UUID> findByUsernameAndPassword(String username, String password);
}
