package com.word_trainer.domain;

import org.springframework.data.annotation.Id;

/**
 * Created by klolo on 26.09.16.
 */
public class User {

    @Id
    public String id;

    public String firstName;

    public User(String firstName) {
        this.firstName = firstName;
    }
}
