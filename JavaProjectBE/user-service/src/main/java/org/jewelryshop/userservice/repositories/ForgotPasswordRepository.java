package org.jewelryshop.userservice.repositories;

import org.jewelryshop.userservice.entities.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword,String> {
    ForgotPassword findByUserId(String userId);
}
