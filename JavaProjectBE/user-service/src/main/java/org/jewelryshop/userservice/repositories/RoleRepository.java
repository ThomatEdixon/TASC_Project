package org.jewelryshop.userservice.repositories;

import org.jewelryshop.userservice.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,String> {
}
