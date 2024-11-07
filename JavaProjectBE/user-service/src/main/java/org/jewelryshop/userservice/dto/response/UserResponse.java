package org.jewelryshop.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jewelryshop.userservice.entities.Role;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String username;

    private String email;

    private String phoneNumber;

    private LocalDateTime dob;

    private String address;

    private Role role;
}
