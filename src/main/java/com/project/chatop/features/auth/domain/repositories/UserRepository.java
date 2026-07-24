package com.project.chatop.features.auth.domain.repositories;

import com.project.chatop.features.auth.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);
}
