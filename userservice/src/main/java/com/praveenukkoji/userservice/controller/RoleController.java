package com.praveenukkoji.userservice.controller;

import com.praveenukkoji.userservice.dto.RoleResponse;
import com.praveenukkoji.userservice.exception.RoleNotFoundException;
import com.praveenukkoji.userservice.model.Role;
import com.praveenukkoji.userservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping(path = "/create")
    public ResponseEntity<?> createRole(@RequestBody Role role) {
        return ResponseEntity.status(201).body(roleRepository.save(role));
    }

    @GetMapping(path = "/get")
    public ResponseEntity<?> getRole(@RequestParam(defaultValue = "", name = "roleId") String roleId)
            throws RoleNotFoundException {
        if (!Objects.equals(roleId, "")) {
            UUID id = UUID.fromString(roleId);

            Optional<Role> role = roleRepository.findById(id);

            if (role.isPresent())
                return ResponseEntity.status(200).body(role.get().getRoleType());
            else
                throw new RoleNotFoundException("role not found");
        } else {
            RoleResponse roleResponse = RoleResponse.builder()
                    .message("role id is empty")
                    .build();
            return ResponseEntity.status(400).body(roleResponse);
        }
    }
}
