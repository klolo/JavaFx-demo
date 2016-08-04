package com.word_trainer.services;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.function.Predicate;

/**
 * Service wykonuje operacje zwiazane z uwierzytelnianiem uzytkownika.
 */
@Component
@Slf4j
public class AuthorizationService {

    @Getter
    private String userId;

    private static final int MIN_LOGIN_LENGTH = 3;

    private static final int MAX_LOGIN_LENGTH = 20;

    public Predicate<String> fieldCorrectPredicate =
            f -> f != null && f.length() > MIN_LOGIN_LENGTH && f.length() < MAX_LOGIN_LENGTH;

    public String getTextHash(final String input) {
        try {
            return Base64
                    .getEncoder()
                    .encodeToString(
                            MessageDigest.getInstance("MD5").digest(input.getBytes()))
                    .replaceAll("[^A-Za-z0-9]", "");
        }
        catch (NoSuchAlgorithmException e) {
            log.error("cannot hash", e);
        }

        return "";
    }

    public boolean loginUser(final String login, final String password) throws NoSuchAlgorithmException {
        if (fieldCorrectPredicate.test(login) && fieldCorrectPredicate.test(password)) {
            log.info("Login user: {} with password: {}", login, password);
            userId = getTextHash(login + password);
            log.debug("userID hash={}", userId);
            return true;
        }

        return false;
    }
}
