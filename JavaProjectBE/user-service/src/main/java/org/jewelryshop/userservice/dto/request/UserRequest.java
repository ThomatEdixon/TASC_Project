package org.jewelryshop.userservice.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.jewelryshop.userservice.validators.DobConstraint;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class UserRequest {
    @Size(min = 8 , message = "USERNAME_INVALID")
    private String username;

    private String password;

    private String email;

    private String phoneNumber;

    @DobConstraint(minAge = 18,message = "INVALID_DOB")
    private LocalDateTime dob;

    private String address;
}
