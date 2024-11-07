package org.jewelryshop.userservice.services.iplm;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.jewelryshop.userservice.dto.request.IntrospectRequest;
import org.jewelryshop.userservice.dto.request.RefreshRequest;
import org.jewelryshop.userservice.dto.request.UserLoginRequest;
import org.jewelryshop.userservice.dto.request.UserLogoutRequest;
import org.jewelryshop.userservice.dto.response.IntrospectResponse;
import org.jewelryshop.userservice.dto.response.UserLoginResponse;
import org.jewelryshop.userservice.entities.InvalidatedToken;
import org.jewelryshop.userservice.entities.User;
import org.jewelryshop.userservice.exceptions.AppException;
import org.jewelryshop.userservice.exceptions.ErrorCode;
import org.jewelryshop.userservice.repositories.InvalidatedTokenRepository;
import org.jewelryshop.userservice.repositories.UserRepository;
import org.jewelryshop.userservice.services.interfaces.IAuthenticationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final UserRepository userRepository;

    private final InvalidatedTokenRepository invalidatedTokenRepository;

    @Value("${jwt.signerKey}")
    private String signerKey;
    @Value("${jwt.valid-duration}")
    private long VALID_DURATION;
    @Value("${jwt.refreshable-duration}")
    private long REFRESHABLE_DURATION;
    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest) throws AppException {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        User user = userRepository
                .findByUsername(userLoginRequest.getUsername());
        if(user == null) throw new AppException(ErrorCode.USER_NOT_EXISTED);
        boolean authenticated = passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword());
        if (!authenticated) throw new AppException(ErrorCode.UNAUTHENTICATED);
        TokenInfo token = generateToken(user);
        return UserLoginResponse.builder()
                .token(token.token)
                .expiryTime(token.expiryDate)
                .build();
    }

    @Override
    public void logout(UserLogoutRequest userLogoutRequest) throws AppException, ParseException, JOSEException {
        try{
            SignedJWT signedJWT = verifyToken(userLogoutRequest.getToken(),true);
            String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jwtId)
                    .expiryTime(expiryTime)
                    .build();
            invalidatedTokenRepository.save(invalidatedToken);
        }catch (AppException exception){

        }
    }
    public IntrospectResponse introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException {
        var token = introspectRequest.getToken();
        boolean isValid = true;

        try {
            verifyToken(token,false);
        } catch (AppException e) {
            isValid = false;
        }

        return IntrospectResponse.builder().valid(isValid).build();
    }

    private TokenInfo generateToken(User user) throws AppException {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        Date issueTime = new Date();
        Date expiryTime = new Date(Instant.ofEpochMilli(issueTime.getTime())
                .plus(VALID_DURATION, ChronoUnit.SECONDS)
                .toEpochMilli());

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername()) // user dang nhap
                .issueTime(issueTime)
                .expirationTime(expiryTime)
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .claim("email",user.getEmail())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header,payload);

        try{
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return new TokenInfo(jwsObject.serialize(), expiryTime);
        }catch (JOSEException e){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    public UserLoginResponse refreshToken(RefreshRequest refreshRequest) throws AppException, ParseException, JOSEException {
        SignedJWT signedJWT = verifyToken(refreshRequest.getToken(),true);
        // logout token hiện tại
        String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken =
                InvalidatedToken.builder().id(jwtId).expiryTime(expiryTime).build();

        invalidatedTokenRepository.save(invalidatedToken);

        // tạo 1 token mới
        String username = signedJWT.getJWTClaimsSet().getSubject();

        User user = userRepository.findByUsername(username);

        if(Objects.isNull(user)){
            throw  new AppException(ErrorCode.UNAUTHENTICATED);
        }

        TokenInfo token = generateToken(user);

        return UserLoginResponse.builder()
                .token(token.token)
                .expiryTime(token.expiryDate)
                .build();
    }

    private SignedJWT verifyToken(String token,boolean isRefresh) throws JOSEException, ParseException, AppException {
        JWSVerifier jwsVerifier = new MACVerifier(signerKey.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(REFRESHABLE_DURATION,ChronoUnit.SECONDS).toEpochMilli())
                :signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean verified = signedJWT.verify(jwsVerifier);
        if (!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);

        if(invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        return signedJWT;
    }
    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (user.getRole()!= null)
            stringJoiner.add("ROLE_" + user.getRole().getName());

        if (!CollectionUtils.isEmpty(user.getRole().getPermissions()))
            user.getRole().getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));

        return stringJoiner.toString();
    }
    private record TokenInfo(String token, Date expiryDate) {}
}
