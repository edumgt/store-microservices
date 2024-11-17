package com.praveenukkoji.userservice.controller;

import com.praveenukkoji.userservice.dto.role.request.CreateRoleRequest;
import com.praveenukkoji.userservice.dto.role.request.UpdateRoleRequest;
import com.praveenukkoji.userservice.exception.error.ValidationException;
import com.praveenukkoji.userservice.exception.role.RoleCreateException;
import com.praveenukkoji.userservice.exception.role.RoleDeleteException;
import com.praveenukkoji.userservice.exception.role.RoleNotFoundException;
import com.praveenukkoji.userservice.exception.role.RoleUpdateException;
import com.praveenukkoji.userservice.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    // create
    @PostMapping(path = "")
    public ResponseEntity<?> createRole(@RequestBody @Valid CreateRoleRequest createRoleRequest)
            throws RoleCreateException, ValidationException {
        return ResponseEntity.status(201).body(roleService.createRole(createRoleRequest));
    }

    // get
    @GetMapping(path = "")
    public ResponseEntity<?> getRole(
            @RequestParam(defaultValue = "", name = "roleId") String roleId
    ) throws RoleNotFoundException, ValidationException {
        return ResponseEntity.status(200).body(roleService.getRole(roleId));
    }

    // update
    @PatchMapping(path = "")
    public ResponseEntity<?> updateRole(
            @RequestBody @Valid UpdateRoleRequest updateRoleRequest
    ) throws RoleNotFoundException, RoleUpdateException, ValidationException {
        roleService.updateRole(updateRoleRequest);
        return ResponseEntity.status(204).body("");
    }

    // delete
    @DeleteMapping(path = "")
    public ResponseEntity<?> deleteRole(
            @RequestParam(defaultValue = "", name = "roleId") String roleId
    ) throws RoleNotFoundException, RoleDeleteException, ValidationException {
        roleService.deleteRole(roleId);
        return ResponseEntity.status(204).body("");
    }

    // get all
    @GetMapping(path = "/all")
    public ResponseEntity<?> getAllRole() {
        return ResponseEntity.status(200).body(roleService.getAllRole());
    }
}
