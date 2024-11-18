package org.jewelryshop.paymentservice.repositories;

import org.jewelryshop.paymentservice.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,String> {
}
