package org.jewelryshop.userservice.services.iplm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jewelryshop.userservice.configurations.CustomJwtDecoder;
import org.jewelryshop.userservice.constants.PredefinedRole;
import org.jewelryshop.userservice.dto.request.ChangePasswordRequest;
import org.jewelryshop.userservice.dto.request.ForgotPasswordRequest;
import org.jewelryshop.userservice.dto.request.UserRequest;
import org.jewelryshop.userservice.dto.request.VerifyRequest;
import org.jewelryshop.userservice.dto.response.UserResponse;
import org.jewelryshop.userservice.entities.ForgotPassword;
import org.jewelryshop.userservice.entities.Role;
import org.jewelryshop.userservice.entities.User;
import org.jewelryshop.userservice.exceptions.AppException;
import org.jewelryshop.userservice.exceptions.ErrorCode;
import org.jewelryshop.userservice.mappers.UserMapper;
import org.jewelryshop.userservice.repositories.ForgotPasswordRepository;
import org.jewelryshop.userservice.repositories.UserRepository;
import org.jewelryshop.userservice.services.UserService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final MailServiceImpl mailService;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final CustomJwtDecoder customJwtDecoder;

    @Override
    public UserResponse createUser(UserRequest userRequest){
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
//    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    public UserResponse updateUser(String id, UserRequest userRequest) {
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
//    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserById(String id) {
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @Override
    public UserResponse getMyInfoFromToken(String token) {
        Jwt decodedJwt = customJwtDecoder.decode(token);

        User user = userRepository.findByUsername(decodedJwt.getSubject());
        if(user == null)
            throw new AppException(ErrorCode.USER_NOT_EXISTED);

        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse changePassword(String username, ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findByUsername(username);
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public boolean forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        User user = userRepository.findByUsername(forgotPasswordRequest.getUsername());
        ForgotPassword forgotPasswordExisting = forgotPasswordRepository.findByUserId(user.getUserId());
        String otp = mailService.generateOTP();
        if(user.getEmail().equals(forgotPasswordRequest.getEmail())){
            if(forgotPasswordExisting == null){
                ForgotPassword forgotPassword = ForgotPassword.builder()
                        .id(UUID.randomUUID().toString())
                        .userId(user.getUserId())
                        .expiryTime(LocalDateTime.now().plusMinutes(1))
                        .otp(otp)
                        .build();
                forgotPasswordRepository.save(forgotPassword);
                mailService.sendEmail(forgotPasswordRequest.getEmail(),otp);
            }else {
                if(LocalDateTime.now().isBefore(forgotPasswordExisting.getExpiryTime())){
                    return true;
                }else {
                    forgotPasswordExisting.setUserId(user.getUserId());
                    forgotPasswordExisting.setExpiryTime(LocalDateTime.now().plusMinutes(1));
                    forgotPasswordExisting.setOtp(otp);
                    forgotPasswordRepository.save(forgotPasswordExisting);
                    mailService.sendEmail(forgotPasswordRequest.getEmail(),otp);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean verifyOTP(String username, VerifyRequest verifyRequest) {
        User user = userRepository.findByUsername(username);
        ForgotPassword forgotPassword = forgotPasswordRepository.findByUserId(user.getUserId());
        if (forgotPassword.getOtp().equals(verifyRequest.getOtp())){
            if(LocalDateTime.now().isBefore(forgotPassword.getExpiryTime())){
                return true;
            }
        }
        return false;
    }

    public static LocalDateTime convertMillisToLocalDateTime(long millis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
    }
}
