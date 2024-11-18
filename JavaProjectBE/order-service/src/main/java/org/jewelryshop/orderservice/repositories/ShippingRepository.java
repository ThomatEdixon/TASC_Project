package org.jewelryshop.orderservice.repositories;

import org.jewelryshop.orderservice.entities.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRepository extends JpaRepository<Shipping,String> {
}
