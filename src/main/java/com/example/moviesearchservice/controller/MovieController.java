package com.example.moviesearchservice.controller;

import com.example.moviesearchservice.model.Movie;
import com.example.moviesearchservice.model.User;
import com.example.moviesearchservice.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @QueryMapping
    public List<Movie> findRecommendedTitlesByGenres(@Argument long userId) {
        return movieService.findRecommendedTitlesByGenres(userId);
    }

    @MutationMapping
    public User newUser() {
        return movieService.newUser();
    }

    @QueryMapping
    public List<Movie> findByOriginalTitle(@Argument long userId, @Argument String originalTitle) {
        return movieService.findByOriginalTitle(userId, originalTitle);
    }


    @QueryMapping
    public List<Movie> findByTitle(@Argument String title) {
        return movieService.findByTitle(title);
    }
}