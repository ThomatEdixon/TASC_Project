package org.jewelryshop.userservice.controllers;

import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.jewelryshop.userservice.dto.request.IntrospectRequest;
import org.jewelryshop.userservice.dto.request.RefreshRequest;
import org.jewelryshop.userservice.dto.request.UserLoginRequest;
import org.jewelryshop.userservice.dto.request.UserLogoutRequest;
import org.jewelryshop.userservice.dto.response.ApiResponse;
import org.jewelryshop.userservice.dto.response.IntrospectResponse;
import org.jewelryshop.userservice.dto.response.UserLoginResponse;
import org.jewelryshop.userservice.exceptions.AppException;
import org.jewelryshop.userservice.services.iplm.AuthenticationService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping(value = "/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/login")
    public ApiResponse<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) throws AppException {
        UserLoginResponse userLoginResponse = authenticationService.login(userLoginRequest);
        return ApiResponse.<UserLoginResponse>builder().data(userLoginResponse).build();
    }
    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest introspectRequest) throws ParseException, JOSEException {
        IntrospectResponse introspectResponse = authenticationService.introspect(introspectRequest);
        return ApiResponse.<IntrospectResponse>builder().data(introspectResponse).build();
    }
    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody UserLogoutRequest request) throws ParseException, JOSEException, AppException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().build();
    }
    @PostMapping("/refresh")
    ApiResponse<UserLoginResponse> authenticate(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException, AppException {
        var result = authenticationService.refreshToken(request);
        return ApiResponse.<UserLoginResponse>builder().data(result).build();
    }

}
