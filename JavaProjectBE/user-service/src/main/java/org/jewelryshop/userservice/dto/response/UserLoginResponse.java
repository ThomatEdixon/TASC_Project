package org.jewelryshop.userservice.dto.response;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginResponse {
    private String token;
    private Date expiryTime;
}
