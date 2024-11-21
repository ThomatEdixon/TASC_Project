package org.jewelryshop.orderservice.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(500, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_EXISTED(404, "User not existed", HttpStatus.NOT_FOUND),

    ;

    ErrorCode(int errorCode, String message, HttpStatusCode statusCode) {
        this.errorCode = errorCode;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int errorCode;
    private final String message;
    private final HttpStatusCode statusCode;
}
