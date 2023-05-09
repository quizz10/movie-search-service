package com.example.moviesearchservice.controller;

import com.example.moviesearchservice.model.AtlasMovie;
import com.example.moviesearchservice.service.AtlasMovieService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor
public class AtlasMovieController {
    private final AtlasMovieService movieService;

    @QueryMapping
    public List<AtlasMovie> findByOriginalTitleStartsWith(@Argument String originalTitle) {
        return movieService.findByOriginalTitle(originalTitle);
    }

    @QueryMapping
    public List<AtlasMovie> findByText(@Argument String text) {
        return movieService.findByText(text);
    }

    @QueryMapping
    public List<AtlasMovie> findAllByText(@Argument String text) {
        return movieService.findAllByText(text);
    }

    @QueryMapping
    public AtlasMovie findByTconst(@Argument String tconst) {
        return movieService.findByTconst(tconst);
    }
}
