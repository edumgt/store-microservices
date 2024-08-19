package com.praveenukkoji.userservice.controller;

import com.praveenukkoji.userservice.dto.error.ValidationResponse;
import com.praveenukkoji.userservice.dto.request.role.CreateRoleRequest;
import com.praveenukkoji.userservice.dto.request.role.UpdateRoleRequest;
import com.praveenukkoji.userservice.exception.role.RoleCreateException;
import com.praveenukkoji.userservice.exception.role.RoleNotFoundException;
import com.praveenukkoji.userservice.exception.role.RoleUpdateException;
import com.praveenukkoji.userservice.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping(path = "")
    public ResponseEntity<?> createRole(@RequestBody @Valid CreateRoleRequest createRoleRequest)
            throws RoleCreateException {
        return ResponseEntity.status(201).body(roleService.createRole(createRoleRequest));
    }

    @GetMapping(path = "")
    public ResponseEntity<?> getRole(
            @RequestParam(defaultValue = "", name = "roleId") String roleId
    ) throws RoleNotFoundException {
        if (Objects.equals(roleId, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("roleId", "role id is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(roleId);
        return ResponseEntity.status(200).body(roleService.getRole(id));

    }

    @PatchMapping(path = "")
    public ResponseEntity<?> updateRole(
            @RequestParam(defaultValue = "", name = "roleId") String roleId,
            @RequestBody @Valid UpdateRoleRequest updateRoleRequest
    ) throws RoleNotFoundException, RoleUpdateException {
        if (Objects.equals(roleId, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("roleId", "role id is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(roleId);
        return ResponseEntity.status(200).body(roleService.updateRole(id, updateRoleRequest));
    }

    @DeleteMapping(path = "")
    public ResponseEntity<?> deleteRole(
            @RequestParam(defaultValue = "", name = "roleId") String roleId
    ) throws RoleNotFoundException {
        if (Objects.equals(roleId, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("roleId", "role id is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(roleId);
        roleService.deleteRole(id);

        return ResponseEntity.status(204).body("");
    }
}
