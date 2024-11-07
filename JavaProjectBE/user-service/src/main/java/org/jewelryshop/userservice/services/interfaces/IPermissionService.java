package org.jewelryshop.userservice.services.interfaces;

import org.jewelryshop.userservice.dto.request.PermissionRequest;
import org.jewelryshop.userservice.dto.response.PermissionResponse;

import java.util.List;

public interface IPermissionService {
    PermissionResponse create(PermissionRequest request);
    List<PermissionResponse> getAll();
    void delete(String permissionName);
}
