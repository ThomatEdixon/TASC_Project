package org.jewelryshop.userservice.services.interfaces;

import org.jewelryshop.userservice.dto.request.UserRequest;
import org.jewelryshop.userservice.dto.response.UserResponse;
import org.jewelryshop.userservice.exceptions.AppException;

import java.util.List;

public interface IUserService {
    UserResponse createUser(UserRequest userRequest) throws AppException;
    List<UserResponse> getAll();
    UserResponse updateUser(String id,UserRequest userRequest) throws AppException;
    void deleteUser(String id);
    UserResponse getUserById(String id) throws AppException;
    UserResponse getMyInfoFromToken() throws AppException;
}
