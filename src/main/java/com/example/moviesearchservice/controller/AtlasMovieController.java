package com.example.moviesearchservice.controller;

import com.example.moviesearchservice.model.AtlasCompiled;
import com.example.moviesearchservice.service.AtlasMovieService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class AtlasMovieController {
    private final AtlasMovieService movieService;
    @QueryMapping
    public AtlasCompiled findByOriginalTitleOrActor(@Argument String titleOrActor) {
        AtlasCompiled moviesAndActors = movieService.findByOriginalTitleOrActor(titleOrActor);

        return moviesAndActors;
    }
}
