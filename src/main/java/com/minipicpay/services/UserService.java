package com.minipicpay.services;

import com.minipicpay.domain.user.User;
import com.minipicpay.domain.user.UserType;
import com.minipicpay.dtos.UserDTO;
import com.minipicpay.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if (sender.getUserType() == UserType.MERCHANT) {
            throw new Exception("Usuário do tipo lojista não pode realizar uma transação");
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Saldo insuficiente");
        }
    }

    public User findUserById(Long id) throws Exception {
        return this.userRepository.findUserById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public User createUser(UserDTO userDTO) {
        User user = new User(userDTO);
        this.saveUser(user);
        return user;
    }

    public void saveUser(User user) {
        this.userRepository.save(user);
    }
}
