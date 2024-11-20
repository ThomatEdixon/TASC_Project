package org.jewelryshop.paymentservice.services;

import org.jewelryshop.paymentservice.dto.request.TransactionRequest;
import org.jewelryshop.paymentservice.entities.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(TransactionRequest transactionRequest);
    void update();
    List<Transaction> getByPaymentId(String paymentId);

}
