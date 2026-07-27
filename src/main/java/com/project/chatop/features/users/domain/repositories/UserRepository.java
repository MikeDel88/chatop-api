package com.project.chatop.features.users.domain.repositories;

import com.project.chatop.features.users.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);
}
