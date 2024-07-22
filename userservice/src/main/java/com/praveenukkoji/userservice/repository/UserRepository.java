package com.praveenukkoji.userservice.repository;

import com.praveenukkoji.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    // for login
    @Query("SELECT u.userId FROM User u WHERE userUsername=?1 AND userPassword=?2")
    Optional<UUID> findByUsernameAndPassword(String username, String password);
}
