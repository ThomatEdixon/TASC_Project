package org.jewelryshop.userservice.repositories;

import org.jewelryshop.userservice.entities.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken,String> {
}
