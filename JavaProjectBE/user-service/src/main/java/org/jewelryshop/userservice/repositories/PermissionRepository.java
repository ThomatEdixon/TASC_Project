package org.jewelryshop.userservice.repositories;

import org.jewelryshop.userservice.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,String> {
}
