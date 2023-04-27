package com.example.moviesearchservice.service;

import com.example.moviesearchservice.model.Cast;
import com.example.moviesearchservice.model.Movie;
import com.example.moviesearchservice.model.Name;
import com.example.moviesearchservice.model.User;
import com.example.moviesearchservice.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {
    private final RatingRepository ratingRepository;
    private final NameRepository nameRepository;
    private final MovieRepository movieRepository;
    private final CastRepository castRepository;
    private final UserRepository userRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final char TITLE_REGEX = '^';


    public User newUser() {
        User user = new User();
        user.setUserId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
        return userRepository.save(user);
    }

    public List<Movie> findByOriginalTitle(long userId, String originalTitle) {
        User user = userRepository.findByUserId(userId);
        List<Movie> movies = movieRepository.findByOriginalTitleStartsWith(TITLE_REGEX + originalTitle);
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

    public List<Movie> findByTitle(String title) {
        return movieRepository.findByOriginalTitleStartsWith(TITLE_REGEX + title);
    }

    public List<Movie> findRecommendedTitlesByGenres(long userId) {
        User user = userRepository.findByUserId(userId);
        List<Movie> recommendedMovies = new ArrayList<>();

        for (String genre : user.getGenres().keySet()) {
            recommendedMovies.addAll(movieRepository.findTwoTitlesByGenre(genre));
        }
        return recommendedMovies;
    }

    private void storeGenreData(User user, List<Movie> movies) {
        final String EMPTY_GENRE = "\\N";
        final int ADD_ONE = 1;
        movies.stream()
                .flatMap(movie -> movie.getGenres().stream())
                .filter(g -> !g.startsWith(EMPTY_GENRE))
                .forEach(genre -> {
                    user.getGenres().merge(genre, ADD_ONE, Integer::sum);
                });
        userRepository.save(user);
    }


}
