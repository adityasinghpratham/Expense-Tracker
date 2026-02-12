package com.project.expensetracker.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "expenses")
@Data
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Double amount;

    private String category;

    private LocalDate date;

    @Column(length = 500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
