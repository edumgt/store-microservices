package com.praveenukkoji.userservice.service;

import com.praveenukkoji.userservice.dto.role.request.CreateRoleRequest;
import com.praveenukkoji.userservice.dto.role.request.UpdateRoleRequest;
import com.praveenukkoji.userservice.dto.role.response.RoleResponse;
import com.praveenukkoji.userservice.exception.error.ValidationException;
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
            throws RoleCreateException, ValidationException {

        String roleType = createRoleRequest.getType().toUpperCase();
        log.info("creating new role = {}", roleType);

        if(roleType.isEmpty()) {
            throw new ValidationException("roleType", "role type cannot be empty");
        }

        try {
            Role newRole = Role.builder()
                    .type(roleType)
                    .build();

            return roleRepository.save(newRole).getId();
        } catch (DataIntegrityViolationException e) {
            throw new RoleCreateException(e.getMostSpecificCause().getMessage());
        } catch (Exception e) {
            throw new RoleCreateException(e.getMessage());
        }
    }

    // get
    public RoleResponse getRole(String id)
            throws RoleNotFoundException, ValidationException {

        log.info("fetching role having id = {}", id);

        if(id.isEmpty()) {
            throw new ValidationException("roleId", "role id cannot be empty");
        }

        UUID roleId = UUID.fromString(id);

        Optional<Role> role = roleRepository.findById(roleId);

        if (role.isPresent())
            return RoleResponse.builder()
                    .id(role.get().getId())
                    .type(role.get().getType())
                    .build();

        throw new RoleNotFoundException("role with id = " + id + " not found");
    }

    // update
    public void updateRole(UpdateRoleRequest updateRoleRequest)
            throws RoleNotFoundException, RoleUpdateException, ValidationException {

        String id = updateRoleRequest.getRoleId();
        String updatedType = updateRoleRequest.getType().toUpperCase();

        log.info("updating role having id = {}", id);

        if(id.isEmpty()) {
            throw new ValidationException("roleId", "role id cannot be empty");
        }

        if(updatedType.isEmpty()) {
            throw new ValidationException("type",  "type cannot be empty");
        }

        try {
            UUID roleId = UUID.fromString(id);
            Optional<Role> role = roleRepository.findById(roleId);

            if (role.isPresent()) {
                Role updatedRole = role.get();
                updatedRole.setType(updatedType);

                roleRepository.save(updatedRole);
            }
            else {
                throw new RoleNotFoundException("role with id = " + id + " not found");
            }
        }
        catch (RoleNotFoundException e) {
            throw new RoleNotFoundException(e.getMessage());
        }
        catch (Exception e) {
            throw new RoleUpdateException(e.getMessage());
        }
    }

    // delete
    public void deleteRole(String id)
            throws RoleNotFoundException, RoleDeleteException, ValidationException {

        log.info("deleting role having id = {}", id);

        if(id.isEmpty()) {
            throw new ValidationException("roleId", "role id cannot be empty");
        }

        try {
            UUID roleId = UUID.fromString(id);

            Optional<Role> role = roleRepository.findById(roleId);

            if(role.isPresent()) {
                roleRepository.deleteById(roleId);
            }
            else {
                throw new RoleNotFoundException("role with id = " + roleId + " not found");
            }
        }
        catch (RoleNotFoundException e) {
            throw new RoleNotFoundException(e.getMessage());
        }
        catch (Exception e) {
            throw new RoleDeleteException(e.getMessage());
        }
    }

    // get all
    public List<RoleResponse> getAllRole() {

        log.info("fetching all roles");

        List<Role> roleList = roleRepository.findAll();

        return roleList.stream().map(role ->
                RoleResponse.builder()
                .id(role.getId())
                .type(role.getType())
                .build()
        ).toList();
    }
}
