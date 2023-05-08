package com.example.moviesearchservice.service;

import com.example.moviesearchservice.model.Cast;
import com.example.moviesearchservice.model.Movie;
import com.example.moviesearchservice.model.Name;
import com.example.moviesearchservice.model.User;
import com.example.moviesearchservice.repository.CastRepository;
import com.example.moviesearchservice.repository.MovieRepository;
import com.example.moviesearchservice.repository.NameRepository;
import com.example.moviesearchservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieService {
    private final NameRepository nameRepository;
    private final MovieRepository movieRepository;
    private final CastRepository castRepository;
    private final UserRepository userRepository;
    private final GenreService genreService;
    private final UserService userService;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final char TITLE_REGEX = '^';


    public User newUser() {
        User user = new User();
        user.setUserId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
        return userRepository.save(user);
    }

    public List<Movie> findByOriginalTitleAndUserId(long userId, String originalTitle) {
        User user = userService.findUserById(userId).get();

        List<Movie> movies = movieRepository.findByOriginalTitleStartsWith(TITLE_REGEX + originalTitle);
        List<Movie> compiledMovies = compileMovies(movies);
        storeGenreData(user, movies);

        return compiledMovies;
    }

    public List<Movie> compileMovies(List<Movie> movies) {
        for (Movie movie : movies) {
            movie.setCasts(getCast(movie.getTconst()));
            movie.getNames().addAll(getActors(movie.getCasts()));
        }
        return movies;
    }

    public List<Movie> findByTitle(String title) {
        List<Movie> movies = movieRepository.findByOriginalTitleStartsWith(TITLE_REGEX + title);

        return compileMovies(movies);
    }



    public List<Movie> findRecommendedTitlesByGenresAndUserId(long userId) {
        Iterator<Entry<String, Integer>> i = genreService.sortGenresByHighestValue(userId);
        List<Movie> movies = new ArrayList<>();
        int amountOfRecommendations = 3;
        int currentValue = -1;
        while (i.hasNext() && amountOfRecommendations > 0) {
            Entry<String, Integer> mp = i.next();
            if (mp.getValue() != currentValue) {
                if (currentValue != -1) {
                    amountOfRecommendations--;
                }
                currentValue = mp.getValue();
            }

            movies.addAll(movieRepository.findTitlesByGenre((mp.getKey()), amountOfRecommendations));

        }

        return compileMovies(movies);
    }

    private void storeGenreData(User user, List<Movie> movies) {
        final String EMPTY_GENRE = "\\N";
        final int ADD_ONE = 1;
        movies.stream()
                .flatMap(movie -> movie.getGenres()
                        .stream())
                .filter(g -> !g.startsWith(EMPTY_GENRE))
                .forEach(genre -> user.getGenres()
                        .merge(genre, ADD_ONE, Integer::sum));
        userRepository.save(user);
    }


    public List<Cast> getCast(String tconst) {
        return castRepository.findByTconst(tconst);
    }

    public List<Name> getActors(List<Cast> casts) {
        return casts.stream()
                .map(cast -> nameRepository.findByNconst(cast.getNconst()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
