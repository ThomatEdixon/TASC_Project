package org.jewelryshop.paymentservice.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(500, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    ORDER_NOT_PENDING(404, "Order is not pending", HttpStatus.NOT_FOUND),
    PAYMENT_NOT_FOUND(404, "Payment nit found", HttpStatus.NOT_FOUND),
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
