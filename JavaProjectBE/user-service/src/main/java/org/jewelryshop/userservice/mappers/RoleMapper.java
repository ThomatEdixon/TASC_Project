package org.jewelryshop.userservice.mappers;

import org.jewelryshop.userservice.dto.request.RoleRequest;
import org.jewelryshop.userservice.dto.response.RoleResponse;
import org.jewelryshop.userservice.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "users",ignore = true)
    Role toRole(RoleRequest request);
    RoleResponse toRoleResponse(Role role);
}
