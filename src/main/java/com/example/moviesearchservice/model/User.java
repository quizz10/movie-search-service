package com.example.moviesearchservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Document(collection = "usercollection")
@Data
public class User {
    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private long userId;

    private Map<String, Integer> genres = new HashMap<>();
    private Map<String, Integer> searchWords = new HashMap<>();

}
