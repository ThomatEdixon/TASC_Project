package org.jewelryshop.userservice.services;

import com.nimbusds.jose.JOSEException;
import org.jewelryshop.userservice.dto.request.IntrospectRequest;
import org.jewelryshop.userservice.dto.request.RefreshRequest;
import org.jewelryshop.userservice.dto.request.UserLoginRequest;
import org.jewelryshop.userservice.dto.request.UserLogoutRequest;
import org.jewelryshop.userservice.dto.response.IntrospectResponse;
import org.jewelryshop.userservice.dto.response.UserLoginResponse;
import org.jewelryshop.userservice.exceptions.AppException;

import java.text.ParseException;

public interface AuthenticationService {
    UserLoginResponse login(UserLoginRequest userLoginRequest) ;
    void logout(UserLogoutRequest userLogoutRequest) throws ParseException, JOSEException;
    IntrospectResponse introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException;
    UserLoginResponse refreshToken(RefreshRequest refreshRequest) throws ParseException, JOSEException;
}
