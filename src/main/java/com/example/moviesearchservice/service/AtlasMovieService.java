package com.example.moviesearchservice.service;

import com.example.moviesearchservice.model.AtlasActor;
import com.example.moviesearchservice.model.AtlasCompiled;
import com.example.moviesearchservice.model.AtlasMovie;
import com.example.moviesearchservice.repository.AtlasActorRepository;
import com.example.moviesearchservice.repository.AtlasMovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AtlasMovieService {
    private AtlasMovieRepository movieRepository;
    private AtlasActorRepository actorRepository;

    public AtlasCompiled findByOriginalTitleOrActor(String titleOrActor) {
        AtlasCompiled atlasCompiled = new AtlasCompiled();
        List<AtlasMovie> movies = movieRepository.findByOriginalTitle(titleOrActor);
        List<AtlasActor> actors = actorRepository.findByActor(titleOrActor);

        atlasCompiled.getMovies().addAll(movies);
        atlasCompiled.getActors().addAll(actors);

        return atlasCompiled;
    }
}
