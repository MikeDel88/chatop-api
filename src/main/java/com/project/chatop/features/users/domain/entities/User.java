package com.project.chatop.features.users.domain.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public record User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id,
    @Column(nullable = false)
    String email,
    @Column(nullable = false)
    String name,
    @Column(nullable = false)
    String password,
    @Column(name = "created_at")
    LocalDateTime createdAt,
    @Column(name = "updated_at")
    LocalDateTime updatedAt
) {}
