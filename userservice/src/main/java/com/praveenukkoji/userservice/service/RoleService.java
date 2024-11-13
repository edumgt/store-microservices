package com.praveenukkoji.userservice.service;

import com.praveenukkoji.userservice.dto.request.role.CreateRoleRequest;
import com.praveenukkoji.userservice.dto.request.role.UpdateRoleRequest;
import com.praveenukkoji.userservice.dto.response.role.RoleResponse;
import com.praveenukkoji.userservice.exception.role.RoleCreateException;
import com.praveenukkoji.userservice.exception.role.RoleDeleteException;
import com.praveenukkoji.userservice.exception.role.RoleNotFoundException;
import com.praveenukkoji.userservice.exception.role.RoleUpdateException;
import com.praveenukkoji.userservice.model.Role;
import com.praveenukkoji.userservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    // create
    public UUID createRole(CreateRoleRequest createRoleRequest)
            throws RoleCreateException {

        log.info("creating new role = {}", createRoleRequest.getType());

        String roleType = createRoleRequest.getType().toUpperCase();

        Role newRole = Role.builder()
                .type(roleType)
                .build();

        try {
            return roleRepository.save(newRole).getId();
        } catch (DataIntegrityViolationException e) {
            throw new RoleCreateException(e.getMostSpecificCause().getMessage());
        } catch (Exception e) {
            throw new RoleCreateException(e.getMessage());
        }
    }

    // get
    public RoleResponse getRole(UUID roleId)
            throws RoleNotFoundException {

        log.info("fetching role having id = {}", roleId);

        Optional<Role> role = roleRepository.findById(roleId);

        if (role.isPresent())
            return RoleResponse.builder()
                    .id(role.get().getId())
                    .type(role.get().getType())
                    .build();

        throw new RoleNotFoundException("role with id = " + roleId + " not found");
    }

    // update
    public UUID updateRole(UpdateRoleRequest updateRoleRequest)
            throws RoleNotFoundException, RoleUpdateException {

        log.info("updating role having id = {}", updateRoleRequest.getRoleId());

        UUID roleId = UUID.fromString(updateRoleRequest.getRoleId());
        Optional<Role> role = roleRepository.findById(roleId);

        if (role.isPresent()) {
            try {
                String newType = updateRoleRequest.getType().toUpperCase();

                Role updatedRole = role.get();
                updatedRole.setType(newType);

                return roleRepository.save(updatedRole).getId();
            } catch (Exception e) {
                throw new RoleUpdateException(e.getMessage());
            }
        }

        throw new RoleNotFoundException("role with id = " + roleId + " not found");
    }

    // delete
    public void deleteRole(UUID roleId)
            throws RoleNotFoundException, RoleDeleteException {

        log.info("deleting role having id = {}", roleId);

        Optional<Role> role = roleRepository.findById(roleId);

        if (role.isPresent()) {
            try {
                roleRepository.deleteById(roleId);
                return;
            } catch (Exception e) {
                throw new RoleDeleteException(e.getMessage());
            }
        }

        throw new RoleNotFoundException("role with id = " + roleId + " not found");
    }

    // get all
    public List<RoleResponse> getAllRole() {

        log.info("fetching all roles");

        List<Role> roleList = roleRepository.findAll();

        return roleList.stream().map(role -> {
            return RoleResponse.builder()
                    .id(role.getId())
                    .type(role.getType())
                    .build();
        }).toList();
    }
}
