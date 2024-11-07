package org.jewelryshop.userservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    private String username;

    private String password;

    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    private LocalDateTime dob;

    private String address;

    @Column(name = "created_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "role_name")
    @JsonIgnore
    private Role role;

}
