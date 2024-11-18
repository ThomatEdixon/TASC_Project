package org.jewelryshop.orderservice.exceptions;


import lombok.extern.slf4j.Slf4j;
import org.jewelryshop.orderservice.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handlerException(RuntimeException exception){
        System.out.println(exception.getMessage());
        return ResponseEntity.badRequest().body(createApiResponse(ErrorCode.UNCATEGORIZED_EXCEPTION));
    }
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity.status(errorCode.getStatusCode()).body(createApiResponse(errorCode));
    }
    private ApiResponse createApiResponse(ErrorCode errorCode){
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return apiResponse;
    }

}
