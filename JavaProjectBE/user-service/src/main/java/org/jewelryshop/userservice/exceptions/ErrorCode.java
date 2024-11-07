package org.jewelryshop.userservice.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(101, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(102, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(103, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(104, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(105, "User not existed", HttpStatus.NOT_FOUND),
    EMAIL_INVALID(106, "Email is invalid", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(107, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(108, "You do not have permission", HttpStatus.FORBIDDEN),
    ROLE_NOT_EXISTED(109, "Role not existed", HttpStatus.NOT_FOUND),
    INVALID_DOB(110, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
