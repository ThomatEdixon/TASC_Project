package org.jewelryshop.userservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jewelryshop.userservice.dto.request.UserRequest;
import org.jewelryshop.userservice.dto.response.ApiResponse;
import org.jewelryshop.userservice.dto.response.UserResponse;
import org.jewelryshop.userservice.entities.User;
import org.jewelryshop.userservice.exceptions.AppException;
import org.jewelryshop.userservice.services.iplm.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
    public ApiResponse<UserResponse> register(@RequestBody @Valid UserRequest userRequest) throws AppException {
        return ApiResponse.<UserResponse>builder()
                .data(userService.createUser(userRequest))
                .build();
    }
    @GetMapping
    ApiResponse<List<UserResponse>> getAllUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .data(userService.getAll())
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUserById(@PathVariable("userId") String userId) throws AppException {
        return ApiResponse.<UserResponse>builder()
                .data(userService.getUserById(userId))
                .build();
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() throws AppException {
        return ApiResponse.<UserResponse>builder()
                .data(userService.getMyInfoFromToken())
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder().data("User has been deleted").build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserRequest userRequest) throws AppException {
        return ApiResponse.<UserResponse>builder()
                .data(userService.updateUser(userId, userRequest))
                .build();
    }

}
