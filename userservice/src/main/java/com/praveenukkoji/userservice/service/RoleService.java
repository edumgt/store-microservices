package com.praveenukkoji.userservice.service;

import com.praveenukkoji.userservice.dto.Response;
import com.praveenukkoji.userservice.dto.request.role.CreateRoleRequest;
import com.praveenukkoji.userservice.dto.request.role.UpdateRoleRequest;
import com.praveenukkoji.userservice.dto.response.role.RoleResponse;
import com.praveenukkoji.userservice.exception.role.RoleNotFoundException;
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

    public RoleResponse createRole(CreateRoleRequest createRoleRequest) {
        Role role = Role.builder()
                .roleType(createRoleRequest.getType().toUpperCase())
                .build();

        role = roleRepository.saveAndFlush(role);

        return RoleResponse.builder()
                .roleId(role.getRoleId())
                .type(role.getRoleType())
                .build();
    }

    public String getRoleType(UUID roleId) throws RoleNotFoundException {
        Optional<Role> role = roleRepository.findById(roleId);

        if (role.isPresent())
            return role.get().getRoleType();
        else
            throw new RoleNotFoundException();
    }

    public RoleResponse updateRole(UUID roleId, UpdateRoleRequest updateRoleRequest)
            throws RoleNotFoundException {
        Optional<Role> role = roleRepository.findById(roleId);

        if (role.isPresent()) {
            Role updatedRole = role.get();

            updatedRole.setRoleType(updateRoleRequest.getType().toUpperCase());

            updatedRole = roleRepository.saveAndFlush(updatedRole);

            return RoleResponse.builder()
                    .roleId(updatedRole.getRoleId())
                    .type(updatedRole.getRoleType())
                    .build();
        }

        throw new RoleNotFoundException();
    }

    public Object deleteRole(UUID roleId) throws RoleNotFoundException {
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
