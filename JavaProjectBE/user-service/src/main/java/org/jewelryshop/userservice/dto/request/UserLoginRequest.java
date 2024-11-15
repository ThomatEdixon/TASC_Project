package org.jewelryshop.userservice.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginRequest {
    private String username;
//    @Pattern(
//            regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$",
//            message = "INVALID_PASSWORD"
//    )
    private String password;
}
