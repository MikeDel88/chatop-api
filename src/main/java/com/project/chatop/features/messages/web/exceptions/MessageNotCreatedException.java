package com.project.chatop.features.messages.web.exceptions;

public class MessageNotCreatedException extends RuntimeException {
    public MessageNotCreatedException() {
        super("Message Not Created");
    }
}
