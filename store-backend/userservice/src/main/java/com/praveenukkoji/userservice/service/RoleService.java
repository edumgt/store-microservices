package com.praveenukkoji.userservice.service;

import com.praveenukkoji.userservice.dto.Response;
import com.praveenukkoji.userservice.dto.request.role.CreateRoleRequest;
import com.praveenukkoji.userservice.dto.request.role.UpdateRoleRequest;
import com.praveenukkoji.userservice.dto.response.role.RoleResponse;
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

    public RoleResponse createRole(CreateRoleRequest createRoleRequest)
            throws RoleCreateException {
        Role newRole = Role.builder()
                .roleType(createRoleRequest.getType().toUpperCase())
                .build();

        try {
            Role role = roleRepository.saveAndFlush(newRole);

            return RoleResponse.builder()
                    .roleId(role.getRoleId())
                    .type(role.getRoleType())
                    .build();
        } catch (Exception e) {
            throw new RoleCreateException();
        }
    }

    public String getRoleType(UUID roleId)
            throws RoleNotFoundException {
        Optional<Role> role = roleRepository.findById(roleId);

        if (role.isPresent())
            return role.get().getRoleType();
        else
            throw new RoleNotFoundException();
    }

    public RoleResponse updateRole(UUID roleId, UpdateRoleRequest updateRoleRequest)
            throws RoleNotFoundException, RoleUpdateException {
        Optional<Role> role = roleRepository.findById(roleId);

        if (role.isPresent()) {

            try {
                role.get().setRoleType(updateRoleRequest.getType().toUpperCase());

                Role updatedRole = roleRepository.saveAndFlush(role.get());

                return RoleResponse.builder()
                        .roleId(updatedRole.getRoleId())
                        .type(updatedRole.getRoleType())
                        .build();
            } catch (Exception e) {
                throw new RoleUpdateException();
            }
        }

        throw new RoleNotFoundException();
    }

    public Object deleteRole(UUID roleId)
            throws RoleNotFoundException {
        Optional<Role> role = roleRepository.findById(roleId);

        if (role.isPresent()) {
            roleRepository.deleteById(roleId);

            return Response.builder()
                    .message("role deleted with roleId = " + roleId)
                    .build();
        }

        throw new RoleNotFoundException();
    }
}
