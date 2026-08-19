package com.project.chatop.config.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Encode le mot de passe en utilisant l'algorithme BCrypt.
 * Fournit également une méthode pour vérifier si un mot de passe correspond à un hash.
 * Utilisé pour sécuriser les mots de passe des utilisateurs.
 */
@Log4j2
@Component
public class HashEncoder {

    private final BCryptPasswordEncoder bcrypt;

    HashEncoder() {
        this.bcrypt = new BCryptPasswordEncoder();
    }

    public String encode(String value) {
        log.debug("encode {}", value);
        String encode = bcrypt.encode(value);
        log.debug("encode {}", encode);
        return encode;
    }

    public Boolean matches(String value, String hash) {
        log.debug("matches {} | {}", value, hash);
        return bcrypt.matches(value, hash);
    }
}
