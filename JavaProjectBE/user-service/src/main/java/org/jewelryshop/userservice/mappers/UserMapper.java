package org.jewelryshop.userservice.mappers;

import org.jewelryshop.userservice.dto.request.UserRequest;
import org.jewelryshop.userservice.dto.response.UserResponse;
import org.jewelryshop.userservice.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "role" ,ignore = true)
    @Mapping(target = "createAt" ,ignore = true)
    @Mapping(target = "updatedAt" ,ignore = true)
    User toUser(UserRequest userRequest);
    UserResponse toUserResponse(User user);

    @Mapping(target = "role", ignore = true)
    void updateUser(@MappingTarget User user, UserRequest request);
}
