package org.jewelryshop.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.jewelryshop.userservice.dto.request.RoleRequest;
import org.jewelryshop.userservice.dto.response.ApiResponse;
import org.jewelryshop.userservice.dto.response.RoleResponse;
import org.jewelryshop.userservice.services.iplm.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest roleRequest) {
        return ApiResponse.<RoleResponse>builder()
                .data(roleService.create(roleRequest))
                .build();
    }
    @GetMapping
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .data(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{roleName}")
    ApiResponse<Void> delete(@PathVariable String roleName) {
        roleService.delete(roleName);
        return ApiResponse.<Void>builder().build();
    }
}
