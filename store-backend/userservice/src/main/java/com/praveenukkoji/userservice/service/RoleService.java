package com.praveenukkoji.userservice.service;

import com.praveenukkoji.userservice.dto.request.role.CreateRoleRequest;
import com.praveenukkoji.userservice.dto.request.role.UpdateRoleRequest;
import com.praveenukkoji.userservice.exception.role.RoleCreateException;
import com.praveenukkoji.userservice.exception.role.RoleNotFoundException;
import com.praveenukkoji.userservice.exception.role.RoleUpdateException;
import com.praveenukkoji.userservice.model.Role;
import com.praveenukkoji.userservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public UUID createRole(CreateRoleRequest createRoleRequest)
            throws RoleCreateException {

        Role newRole = Role.builder()
                .type(createRoleRequest.getType().toUpperCase())
                .build();

        try {
            return roleRepository.save(newRole).getId();
        } catch (Exception e) {
            throw new RoleCreateException();
        }
    }

    public String getRoleType(UUID roleId)
            throws RoleNotFoundException {

        Optional<Role> role = roleRepository.findById(roleId);

        if (role.isPresent())
            return role.get().getType();
        else
            throw new RoleNotFoundException();
    }

    public UUID updateRole(UUID roleId, UpdateRoleRequest updateRoleRequest)
            throws RoleNotFoundException, RoleUpdateException {

        Optional<Role> role = roleRepository.findById(roleId);

        if (role.isPresent()) {

            try {
                role.get().setType(updateRoleRequest.getType().toUpperCase());

                return roleRepository.save(role.get()).getId();
            } catch (Exception e) {
                throw new RoleUpdateException();
            }
        }

        throw new RoleNotFoundException();
    }

    public UUID deleteRole(UUID roleId)
            throws RoleNotFoundException {

        Optional<Role> role = roleRepository.findById(roleId);

        if (role.isPresent()) {
            roleRepository.deleteById(roleId);

            return roleId;
        }

        throw new RoleNotFoundException();
    }
}
