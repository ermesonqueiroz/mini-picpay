package com.minipicpay.domain.user;

import com.minipicpay.dtos.UserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String document;
    @Column(unique = true)
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User(UserDTO userDTO) {
        this.firstName = userDTO.firstName();
        this.lastName = userDTO.lastName();
        this.balance = userDTO.balance();
        this.userType = userDTO.userType();
        this.password = userDTO.password();
        this.email = userDTO.email();
        this.document = userDTO.document();
    }
}
