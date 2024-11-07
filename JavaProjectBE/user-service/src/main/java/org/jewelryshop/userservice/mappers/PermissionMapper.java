package org.jewelryshop.userservice.mappers;

import org.jewelryshop.userservice.dto.request.PermissionRequest;
import org.jewelryshop.userservice.dto.response.PermissionResponse;
import org.jewelryshop.userservice.entities.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
