package com.project.chatop.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Encode le mot de passe en utilisant l'algorithme BCrypt.
 * Fournit également une méthode pour vérifier si un mot de passe correspond à un hash.
 * Utilisé pour sécuriser les mots de passe des utilisateurs.
 */
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
