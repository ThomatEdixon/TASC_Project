package org.jewelryshop.productservice.exceptions;

import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.jewelryshop.productservice.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private static final String MIN_ATTRIBUTE = "min";

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
    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));

        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }
    private ApiResponse createApiResponse(ErrorCode errorCode){
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setErrorCode(errorCode.getErrorCode());
        apiResponse.setMessage(errorCode.getMessage());
        return apiResponse;
    }

}
