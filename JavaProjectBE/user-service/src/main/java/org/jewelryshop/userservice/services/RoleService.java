package org.jewelryshop.userservice.services;

import org.jewelryshop.userservice.dto.request.RoleRequest;
import org.jewelryshop.userservice.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse create(RoleRequest request);
    List<RoleResponse> getAll();
    void delete(String roleName);
}
