package com.example.moviesearchservice.controller;

import com.example.moviesearchservice.model.Cast;
import com.example.moviesearchservice.model.Movie;
import com.example.moviesearchservice.model.Name;
import com.example.moviesearchservice.model.User;
import com.example.moviesearchservice.repository.*;
import com.example.moviesearchservice.service.SequenceGeneratorService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MovieController {
    private final RatingRepository ratingRepository;
    private final NameRepository nameRepository;
    private final MovieRepository movieRepository;
    private final CastRepository castRepository;
    private final UserRepository userRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    public MovieController(RatingRepository ratingRepository, NameRepository nameRepository, MovieRepository movieRepository, CastRepository castRepository, UserRepository userRepository, SequenceGeneratorService sequenceGeneratorService) {
        this.ratingRepository = ratingRepository;
        this.nameRepository = nameRepository;
        this.movieRepository = movieRepository;
        this.castRepository = castRepository;
        this.userRepository = userRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @QueryMapping
    public List<Movie> findRecommendedTitlesByGenres(@Argument long userId) {
        User user = userRepository.findByUserId(userId);
        List<Movie> recommendedMovies = new ArrayList<>();

        for (String genre : user.getGenres().keySet()) {
            recommendedMovies.addAll(movieRepository.findTwoTitlesByGenre(genre));
        }

        return recommendedMovies;
    }

    @MutationMapping
    public User newUser() {
        User user = new User();
        user.setUserId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
        return userRepository.save(user);
    }

    @QueryMapping
    public List<Movie> findByOriginalTitle(@Argument long userId, @Argument String originalTitle) {
        User user = userRepository.findByUserId(userId);
        List<Movie> movies = movieRepository.findByOriginalTitleStartsWith("^" + originalTitle);
        for (Movie movie : movies) {
            if (ratingRepository.findByTconst(movie.getTconst()) != null) {
                movie.setAverageRating(ratingRepository.findByTconst(movie.getTconst()).getAverageRating());
                movie.setCasts(castRepository.findByTconst(movie.getTconst()));
                for (Cast cast : movie.getCasts()) {
                    Name name = nameRepository.findByNconst(cast.getNconst());
                    if (name != null) {
                        movie.getNames().add(name);
                    }
                }
            }
        }

        storeGenreData(user, movies);

        return movies;
    }

    private void storeGenreData(User user, List<Movie> movies) {
        movies.stream()
                .flatMap(movie -> movie.getGenres().stream())
                .filter(g -> !g.startsWith("\\N"))
                .forEach(genre -> {
                    user.getGenres().merge(genre, 1, Integer::sum);
                });
        userRepository.save(user);
    }

    @QueryMapping
    public List<Movie> findByTitle(@Argument String title) {
        return movieRepository.findByOriginalTitleStartsWith("^" + title);
    }
}