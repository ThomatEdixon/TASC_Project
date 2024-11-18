package org.jewelryshop.orderservice.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserResponse {
    private String userId;

    private String username;

    private String email;

    private String phoneNumber;

    private LocalDateTime dob;

    private String address;
}
