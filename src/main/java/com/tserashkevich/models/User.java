package com.tserashkevich.models;

import com.tserashkevich.models.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 30, message = "Логин должен быть от 2 до 30 символов длиной")
    @Column(name = "login", length = 30, nullable = false, unique = true)
    private String login;

    @Size(min = 6, message = "Пароль должен содержать более 6 символов")
    @Column(name = "password", nullable = false)
    private String password;

    @Size(min = 2, max = 30, message = "ФИО должно быть от 2 до 30 символов длиной")
    @Column(name = "username", length = 30, nullable = false)
    private String username;

    @Size(min = 9, max = 9, message = "Номер телефона должен содержать 9 цифр")
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role = Role.USER;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Comment> comments;
}
