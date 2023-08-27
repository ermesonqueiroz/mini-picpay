package com.minipicpay.controllers;

import com.minipicpay.domain.transaction.Transaction;
import com.minipicpay.dtos.TransactionDTO;
import com.minipicpay.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO transactionDTO) throws Exception {
        Transaction transaction = this.transactionService.createTransaction(transactionDTO);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
}
