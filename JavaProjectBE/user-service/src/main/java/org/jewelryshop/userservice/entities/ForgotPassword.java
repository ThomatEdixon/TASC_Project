package org.jewelryshop.userservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "forgot_password")
public class ForgotPassword {
    @Id
    private String id;

    @Column(name = "expiry_time")
    private LocalDateTime expiryTime;

    @Column(name = "user_id")
    private String userId;

    private String otp;
}
