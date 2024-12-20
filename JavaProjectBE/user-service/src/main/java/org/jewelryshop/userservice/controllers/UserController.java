package org.jewelryshop.userservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jewelryshop.userservice.dto.request.ChangePasswordRequest;
import org.jewelryshop.userservice.dto.request.ForgotPasswordRequest;
import org.jewelryshop.userservice.dto.request.UserRequest;
import org.jewelryshop.userservice.dto.request.VerifyRequest;
import org.jewelryshop.userservice.dto.response.ApiResponse;
import org.jewelryshop.userservice.dto.response.UserResponse;
import org.jewelryshop.userservice.exceptions.AppException;
import org.jewelryshop.userservice.services.iplm.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    @PostMapping
    public ApiResponse<UserResponse> register(@RequestBody @Valid UserRequest userRequest) throws AppException {
        return ApiResponse.<UserResponse>builder()
                .data(userService.createUser(userRequest))
                .build();
    }
    @PostMapping("/forgotPassword")
    public ApiResponse<Boolean> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest ) {
        return ApiResponse.<Boolean>builder()
                .data(userService.forgotPassword(forgotPasswordRequest))
                .build();
    }
    @PostMapping("/changePassword/{username}")
    public ApiResponse<UserResponse> changePassword(@PathVariable String username
            , @RequestBody ChangePasswordRequest changePasswordRequest) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.changePassword(username,changePasswordRequest))
                .build();
    }

    @PostMapping("/verify/{username}")
    public ApiResponse<Boolean> verify(@PathVariable String username
            , @RequestBody VerifyRequest verifyRequest)  {
        return ApiResponse.<Boolean>builder()
                .data(userService.verifyOTP(username,verifyRequest))
                .build();
    }
    @GetMapping
    ApiResponse<List<UserResponse>> getAllUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .data(userService.getAll())
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUserById(@PathVariable("userId") String userId) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.getUserById(userId))
                .build();
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfoFormToken(@RequestParam String token){
        return ApiResponse.<UserResponse>builder()
                .data(userService.getMyInfoFromToken(token))
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder().data("User has been deleted").build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserRequest userRequest) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.updateUser(userId, userRequest))
                .build();
    }

}
