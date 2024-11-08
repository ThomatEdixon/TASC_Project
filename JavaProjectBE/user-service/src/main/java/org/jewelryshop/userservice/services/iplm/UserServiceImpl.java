package org.jewelryshop.userservice.services.iplm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jewelryshop.userservice.constants.PredefinedRole;
import org.jewelryshop.userservice.dto.request.UserRequest;
import org.jewelryshop.userservice.dto.response.UserResponse;
import org.jewelryshop.userservice.entities.Role;
import org.jewelryshop.userservice.entities.User;
import org.jewelryshop.userservice.exceptions.AppException;
import org.jewelryshop.userservice.exceptions.ErrorCode;
import org.jewelryshop.userservice.mappers.UserMapper;
import org.jewelryshop.userservice.repositories.UserRepository;
import org.jewelryshop.userservice.services.interfaces.UserService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserRequest userRequest) throws AppException {
        if (userRepository.existsByUsername(userRequest.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);

        User newUser = userMapper.toUser(userRequest);
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        Role userRole = Role.builder()
                .name(PredefinedRole.ROLE_USER)
                .description("User role ")
                .build();

        newUser.setCreateAt(convertMillisToLocalDateTime(System.currentTimeMillis()));
        newUser.setUpdatedAt(convertMillisToLocalDateTime(System.currentTimeMillis()));

        newUser.setRole(userRole);


        return userMapper.toUserResponse(userRepository.save(newUser));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    public UserResponse updateUser(String id, UserRequest userRequest) throws AppException {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserById(String id) throws AppException {
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @Override
    public UserResponse getMyInfoFromToken() throws AppException {
        SecurityContext context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name);
        if(user == null)
            throw new AppException(ErrorCode.USER_NOT_EXISTED);

        return userMapper.toUserResponse(user);
    }

    public static LocalDateTime convertMillisToLocalDateTime(long millis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
    }
}
