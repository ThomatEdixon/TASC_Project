package org.jewelryshop.paymentservice.repositories;

import org.jewelryshop.paymentservice.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,String> {
}
