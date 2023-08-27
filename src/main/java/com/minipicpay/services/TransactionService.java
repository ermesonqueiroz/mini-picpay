package com.minipicpay.services;

import com.minipicpay.domain.transaction.Transaction;
import com.minipicpay.domain.user.User;
import com.minipicpay.dtos.TransactionDTO;
import com.minipicpay.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    public Transaction createTransaction(TransactionDTO transactionDTO) throws Exception {
        User sender = this.userService.findUserById(transactionDTO.senderId());
        User receiver = this.userService.findUserById(transactionDTO.receiverId());

        this.userService.validateTransaction(sender, transactionDTO.value());

        boolean isAuthorized = this.authorizeTransaction(sender, transactionDTO.value());
        if (!isAuthorized) throw new Exception("Transação não autorizada");

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDTO.value());
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setCreatedAt(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transactionDTO.value()));
        receiver.setBalance(receiver.getBalance().add(transactionDTO.value()));

        this.transactionRepository.save(transaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        this.notificationService.sendNotification(sender, "Transação realizada com sucesso");
        this.notificationService.sendNotification(receiver, "Transação recebida");

        return transaction;
    }

    public boolean authorizeTransaction(User sender, BigDecimal value) {
        ResponseEntity<Map> authorizationResponse = this.restTemplate.getForEntity("https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6", Map.class);
        return authorizationResponse.getStatusCode() == HttpStatus.OK && ((String) authorizationResponse.getBody().get("message")).equalsIgnoreCase("Autorizado");
    }
}
