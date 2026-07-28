package com.project.chatop.features.messages.domain.repositories;

import com.project.chatop.features.messages.domain.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
