package org.jewelryshop.paymentservice.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    ORDER_NOT_PENDING(121, "Order is not pending", HttpStatus.NOT_FOUND),
    PAYMENT_NOT_FOUND(122, "Payment nit found", HttpStatus.NOT_FOUND),
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
