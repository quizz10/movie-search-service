package com.example.moviesearchservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Document(collection = "usercollection")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private long userId;

    Map<String, Integer> genres;
    Map<String, Integer> searchWords;


    public Map<String, Integer> getGenres() {
        if (genres == null)
            genres = new HashMap<>();
        return genres;
    }

    public Map<String, Integer> getSearchWords() {
        if (searchWords == null)
            searchWords = new HashMap<>();
        return searchWords;
    }
}
