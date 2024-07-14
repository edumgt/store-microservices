package com.praveenukkoji.userservice.controller;

import com.praveenukkoji.userservice.dto.Response;
import com.praveenukkoji.userservice.dto.request.UpdateRoleRequest;
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
        role = roleRepository.saveAndFlush(role);
        return ResponseEntity.status(201).body(role.getRoleId().toString());
    }

    @GetMapping(path = "/get")
    public ResponseEntity<?> getRole(
            @RequestParam(defaultValue = "", name = "roleId") String roleId
    ) throws RoleNotFoundException {
        if (!Objects.equals(roleId, "")) {
            UUID id = UUID.fromString(roleId);

            Optional<Role> role = roleRepository.findById(id);

            if (role.isPresent())
                return ResponseEntity.status(200).body(role.get().getRoleType());
            else
                throw new RoleNotFoundException();
        } else {
            Response response = Response.builder()
                    .message("role id is empty")
                    .build();
            return ResponseEntity.status(400).body(response);
        }
    }

    @PatchMapping(path = "/update")
    public ResponseEntity<?> updateRole(
            @RequestParam(defaultValue = "", name = "roleId") String roleId,
            @RequestBody UpdateRoleRequest updateRoleRequest
    ) throws RoleNotFoundException {
        if (!Objects.equals(roleId, "")) {
            UUID id = UUID.fromString(roleId);
            Optional<Role> role = roleRepository.findById(id);
            if (role.isPresent()) {
                role.get().setRoleType(updateRoleRequest.getRoleType());
                Role updatedRole = roleRepository.saveAndFlush(role.get());
                return ResponseEntity.status(200).body(updatedRole);
            }
            throw new RoleNotFoundException();
        } else {
            Response response = Response.builder()
                    .message("role id is empty")
                    .build();
            return ResponseEntity.status(400).body(response);
        }
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> deleteRole(
            @RequestParam(defaultValue = "", name = "roleId") String roleId
    ) throws RoleNotFoundException {
        if (!Objects.equals(roleId, "")) {
            UUID id = UUID.fromString(roleId);
            Optional<Role> role = roleRepository.findById(id);
            if (role.isPresent()) {
                roleRepository.deleteById(id);
                Response response = Response.builder()
                        .message("delete role with id = " + roleId)
                        .build();
                return ResponseEntity.status(200).body(response);
            }
            throw new RoleNotFoundException();
        } else {
            Response response = Response.builder()
                    .message("role id is empty")
                    .build();
            return ResponseEntity.status(400).body(response);
        }
    }
}
