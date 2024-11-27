package org.jewelryshop.userservice.services;

import org.jewelryshop.userservice.dto.request.ChangePasswordRequest;
import org.jewelryshop.userservice.dto.request.ForgotPasswordRequest;
import org.jewelryshop.userservice.dto.request.UserRequest;
import org.jewelryshop.userservice.dto.request.VerifyRequest;
import org.jewelryshop.userservice.dto.response.UserResponse;
import org.jewelryshop.userservice.exceptions.AppException;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    List<UserResponse> getAll();
    UserResponse updateUser(String id,UserRequest userRequest) ;
    void deleteUser(String id);
    UserResponse getUserById(String id) ;
    UserResponse getMyInfoFromToken(String token) ;
    UserResponse changePassword(String userId, ChangePasswordRequest changePasswordRequest) ;
    boolean forgotPassword(ForgotPasswordRequest forgotPasswordRequest);
    boolean verifyOTP(String userId, VerifyRequest verifyRequest);

}
