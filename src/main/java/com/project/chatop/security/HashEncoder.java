package com.project.chatop.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class HashEncoder {

    private final BCryptPasswordEncoder bcrypt;

    HashEncoder() {
        this.bcrypt = new BCryptPasswordEncoder();
    }

    public String encode(String value) {
        return bcrypt.encode(value);
    }

    public Boolean matches(String value, String hash) {
        return bcrypt.matches(value, hash);
    }
}
