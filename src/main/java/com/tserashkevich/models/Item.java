package com.tserashkevich.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "items")
public class Item {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 40, message = "Название должно быть от 2 до 40 символов длиной")
    @Column(name = "name", length = 40, nullable = false)
    private String name;

    @Min(value = 0, message = "Цена должна быть не меньше 0")
    @Max(value = 999999, message = "Цена должна быть не больше 1000000")
    @NotNull(message = "Заполните поле")
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Size(max = 200, message = "Описание должно быть длиной менее 200 символов")
    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "photo_name")
    private String photoName;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<Order> orders;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;
}
