package org.jewelryshop.userservice.repositories;

import org.jewelryshop.userservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
    boolean existsByUsername(String username);
    User findByUsername(String username);
}
