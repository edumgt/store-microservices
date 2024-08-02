package com.praveenukkoji.userservice.controller;

import com.praveenukkoji.userservice.dto.Response;
import com.praveenukkoji.userservice.dto.request.role.CreateRoleRequest;
import com.praveenukkoji.userservice.dto.request.role.UpdateRoleRequest;
import com.praveenukkoji.userservice.exception.role.RoleCreateException;
import com.praveenukkoji.userservice.exception.role.RoleNotFoundException;
import com.praveenukkoji.userservice.exception.role.RoleUpdateException;
import com.praveenukkoji.userservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping(path = "/create")
    public ResponseEntity<?> createRole(@RequestBody CreateRoleRequest createRoleRequest)
            throws RoleCreateException {
        return ResponseEntity.status(201).body(roleService.createRole(createRoleRequest));
    }

    @GetMapping(path = "/get-role-type")
    public ResponseEntity<?> getRoleType(
            @RequestParam(defaultValue = "", name = "roleId") String roleId
    ) throws RoleNotFoundException {

        if (Objects.equals(roleId, "")) {
            Response response = Response.builder()
                    .message("role id is empty")
                    .build();
            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(roleId);
        return ResponseEntity.status(200).body(roleService.getRoleType(id));

    }

    @PatchMapping(path = "/update")
    public ResponseEntity<?> updateRole(
            @RequestParam(defaultValue = "", name = "roleId") String roleId,
            @RequestBody UpdateRoleRequest updateRoleRequest
    ) throws RoleNotFoundException, RoleUpdateException {

        if (Objects.equals(roleId, "")) {
            Response response = Response.builder()
                    .message("role id is empty")
                    .build();
            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(roleId);
        return ResponseEntity.status(200).body(roleService.updateRole(id, updateRoleRequest));
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> deleteRole(
            @RequestParam(defaultValue = "", name = "roleId") String roleId
    ) throws RoleNotFoundException {

        if (Objects.equals(roleId, "")) {
            Response response = Response.builder()
                    .message("role id is empty")
                    .build();
            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(roleId);
        return ResponseEntity.status(200).body(roleService.deleteRole(id));
    }
}
