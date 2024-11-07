package org.jewelryshop.userservice.services.interfaces;

import com.nimbusds.jose.JOSEException;
import org.jewelryshop.userservice.dto.request.UserLoginRequest;
import org.jewelryshop.userservice.dto.request.UserLogoutRequest;
import org.jewelryshop.userservice.dto.response.UserLoginResponse;
import org.jewelryshop.userservice.exceptions.AppException;

import java.text.ParseException;

public interface IAuthenticationService {
    UserLoginResponse login(UserLoginRequest userLoginRequest) throws AppException;
    void logout(UserLogoutRequest userLogoutRequest) throws AppException, ParseException, JOSEException;
}
