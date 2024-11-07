package org.jewelryshop.userservice.services.iplm;

import lombok.RequiredArgsConstructor;
import org.jewelryshop.userservice.dto.request.PermissionRequest;
import org.jewelryshop.userservice.dto.response.PermissionResponse;
import org.jewelryshop.userservice.entities.Permission;
import org.jewelryshop.userservice.mappers.PermissionMapper;
import org.jewelryshop.userservice.repositories.PermissionRepository;
import org.jewelryshop.userservice.services.interfaces.IPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class PermissionService implements IPermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;
    @Override
    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    public List<PermissionResponse> getAll() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    @Override
    public void delete(String permissionName) {
        permissionRepository.deleteById(permissionName);
    }
}
