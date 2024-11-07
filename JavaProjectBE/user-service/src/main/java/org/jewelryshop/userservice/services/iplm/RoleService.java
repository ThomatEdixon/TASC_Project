package org.jewelryshop.userservice.services.iplm;

import lombok.RequiredArgsConstructor;
import org.jewelryshop.userservice.dto.request.RoleRequest;
import org.jewelryshop.userservice.dto.response.RoleResponse;
import org.jewelryshop.userservice.entities.Permission;
import org.jewelryshop.userservice.entities.Role;
import org.jewelryshop.userservice.mappers.RoleMapper;
import org.jewelryshop.userservice.repositories.PermissionRepository;
import org.jewelryshop.userservice.repositories.RoleRepository;
import org.jewelryshop.userservice.services.interfaces.IRoleService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;
    @Override
    public RoleResponse create(RoleRequest request) {
        Role role = roleMapper.toRole(request);

        List<Permission> permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    @Override
    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    @Override
    public void delete(String roleName) {
        roleRepository.deleteById(roleName);
    }
}
