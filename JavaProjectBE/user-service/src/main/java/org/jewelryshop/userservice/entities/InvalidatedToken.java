package org.jewelryshop.userservice.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invalidate_token")
public class InvalidatedToken {
    @Id
    private String id;
    @Column(name = "expiry_time")
    private Date expiryTime;
}
