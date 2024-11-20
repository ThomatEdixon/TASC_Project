package org.jewelryshop.paymentservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.jewelryshop.paymentservice.dto.request.TransactionRequest;
import org.jewelryshop.paymentservice.entities.Payment;
import org.jewelryshop.paymentservice.entities.Transaction;
import org.jewelryshop.paymentservice.repositories.PaymentRepository;
import org.jewelryshop.paymentservice.repositories.TransactionRepository;
import org.jewelryshop.paymentservice.services.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public Transaction createTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = Transaction.builder()
                .transactionCode(transactionRequest.getTransactionCode())
                .amount(transactionRequest.getAmount())
                .transactionStatus(transactionRequest.getTransactionStatus())
                .paymentMethod(transactionRequest.getPaymentMethod())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        Payment payment = paymentRepository.findById(transactionRequest.getPaymentId()).orElseThrow();
        transaction.setPayment(payment);
        return transactionRepository.save(transaction);
    }

    @Override
    public void update() {

    }

    @Override
    public List<Transaction> getByPaymentId(String paymentId) {

        return null;
    }
}
